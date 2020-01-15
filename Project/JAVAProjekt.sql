create database JAVAProjektMLalic
go

use JAVAProjektMLalic
go

create table PersonnelType
(
	IDPersonnelType int primary key identity,
	Name nvarchar(50)
)
go

insert into PersonnelType
values ('Reception executive'), ('Physician/Doctor')
go


create table Personnel
(
	IDPersonel int primary key identity,
	PersonelTypeID int foreign key references PersonnelType(IDPersonnelType) not null,
	Username nvarchar(50) not null unique,
	FirstName nvarchar(50) not null,
	MiddleName nvarchar(50) not null,
	Surname nvarchar(50) not null,
	Title nvarchar(50) not null,
	PasswordHash binary(64) not null,
	Salt uniqueidentifier not null,
	Valid bit not null default(1)
)
go

create table Person
(
	IDPerson int primary key identity,
	FirstName nvarchar(50) not null,
	MiddleName nvarchar(50) null,
	Surname nvarchar(50) not null,
	Sex char(1) null,
	DateOfBirth date null,
	TelephoneHome nvarchar(15) null,
	TelephoneWork nvarchar(15) null,
	Mobile nvarchar(15) null,
	Pager nvarchar(15) null,
	Fax nvarchar(15) null,
	Email nvarchar(50) null,
	PresentAddress nvarchar(200) null,
	PermanentAddress nvarchar(200) null
)
go

create table NextOfKin
(
	IDNextOfKin int primary key identity,
	PersonID int foreign key references Person(IDPerson) not null,
	RelationshipToOutpatient nvarchar(50) not null
)
go

create table BloodType
(
	IDBloodType int primary key identity,
	Name nvarchar(3) not null
)
go

insert into BloodType values
('0-'), ('0+'), ('A-'), ('A+'), ('B-'), ('B+'),('AB-'), ('AB+')
go


create table MaritalStatus
(
	IDMaritalStatus int primary key identity,
	Name nvarchar(50)
)
go

insert into MaritalStatus
values ('Single'), ('Married'), ('Widowed'), ('Divorced')
go


create table Outpatient
(
	IDOutpatient int primary key identity,
	PersonID int foreign key references Person(IDPerson) not null,
	NextOfKinID int foreign key references NextOfKin(IDNextOfKin) not null,
	MaritalStatus int foreign key references MaritalStatus(IDMaritalStatus) null,
	BloodTypeID int foreign key references BloodType(IDBloodType) null,
	ComprehensiveFormFilled bit not null,
	NumberOfDependants int null,
	Height float null,
	Weight float null,
	Occupation nvarchar(50) null,
	GrossAnnualIncome int null,
	Vegetarian bit null,
	Smoker bit null,
	AverageCigarettesPerDay int null,
	ConsumeAlcoholicBeverage bit null,
	AverageDrinksPerDay int null,
	UseStimulants bit null,
	Stimulants nvarchar(50) null,
	CoffeOrTeaPerDay int null,
	SoftDrinksPerDay int null,
	HaveRegularMeals bit null,
	PredominantlyEatsHome bit null,
	StatementOfComplaint nvarchar(200) null,
	HistoryOfPreviousTreatment nvarchar(200) null,
	PhysicianHospitalTreated nvarchar(200) null,
	Diabetic bit null,
	Hypertensive bit null,
	CardiacCondition bit null,
	RaspiratoryCondition bit null,
	DigestiveCondition bit null,
	OrthopedicCondition bit null,
	MuscularCondition bit null,
	NeurologicalCondition bit null,
	KnownAllergies nvarchar(200) null,
	KnownAdverseReactionToSpecificDrugs nvarchar(200) null,
	MayorSurgeriesHistory nvarchar(200) null
)
go

create table EventType
(
	IDEventType int primary key identity,
	Name nvarchar(50)
)
go

insert into EventType
values ('Appointment'), ('Lab test'), ('Prescription'), ('Diagnosis')
go


create table PersonelOutpatientEvent
(
	IDEvent int primary key identity,
	OutpatientID int foreign key references Outpatient(IDOutpatient) not null,
	PersonelID int foreign key references Personnel(IDPersonel) not null,
	EventTypeID int foreign key references EventType(IDEventType) not null,
	AppointedDate Date not null,
	Comment nvarchar(200) null
)
go

create table Outpatient_Personel
(
	IDOutpatientDoctor int primary key identity,
	OutpatientID int foreign key references Outpatient(IDOutpatient) not null,
	PersonelID int foreign key references Personnel(IDPersonel) not null
)
go

create table PaymentType
(
	IDPaymentType int primary key identity,
	Name nvarchar(50)
)
go

insert into PaymentType
values ('Cash'), ('Credit card')
go


create table EventPayment
(
	IDEventPayment int primary key identity,
	EventID int foreign key references PersonelOutpatientEvent(IDEvent) not null,
	PaymentTypeID int foreign key references PaymentType(IDPaymentType) not null,
	Amount float not null
)
go

create table NewPatientDate
(
	IDDate int primary key identity,
	Date date not null default(getdate())
)
go


--reports
create proc GetFollowUpPatients
as
begin
	select datediff(day, AppointedDate, getdate()) as [Date], COUNT(*) as [count]
	from PersonelOutpatientEvent
	where datediff(day, AppointedDate, getdate()) between 0 and 30
	group by AppointedDate
end
go

create proc GetNewPatients
as
begin
	select datediff(day, Date, getdate()) as [Date], COUNT(*) as [count]
	from NewPatientDate
	where datediff(day, Date, getdate()) between 0 and 30
	group by Date
end
go

create proc GetAveragePatientsHandledMonth
as
begin
	 select(convert(float,(select (COUNT(*))
		from PersonelOutpatientEvent
		where datediff(day, AppointedDate, getdate()) between 0 and 30))	/
		(select COUNT(*)
		from Personnel
		where PersonelTypeID=2
		and Valid=1))
	as Average
end
go

create proc getChargedSummaryToday
as
begin
	select e.EventTypeID, SUM(ep.Amount) as Amount
	from EventPayment as ep
	left join PersonelOutpatientEvent as e
	on ep.EventID=e.IDEvent
	where e.AppointedDate=CONVERT(date, GETDATE())
	group by e.EventTypeID
end
go


--personel
create proc GetPersonel
as
begin
	select IDPersonel, PersonelTypeID, Username, FirstName, MiddleName, Surname, Title
	from Personnel
	where Valid=1
end
go

create proc GetPersonelByID
	@id int
as
begin
	select top 1 IDPersonel, PersonelTypeID, Username, FirstName, MiddleName, Surname, Title
	from Personnel
	where Valid=1
	and IDPersonel=@id
end
go

create proc InsertPersonel
	@PersonelTypeID int, @Username nvarchar(50), @FirstName nvarchar(50), @MiddleName nvarchar(50),
	@Surname nvarchar(50), @Title nvarchar(50), @Password nvarchar(max), @ID int output
as
begin
	declare @salt uniqueidentifier = newid()
	insert into Personnel 
	values(@PersonelTypeID, @Username, @FirstName, @MiddleName, @Surname, @Title,
	HASHBYTES('SHA2_512', @Password+CAST(@salt AS NVARCHAR(36))),
	@salt, 1)
	set @ID=scope_identity()
end
go

declare @id int = 0
exec InsertPersonel 1, 'recp', 'Mislav', '', 'Laliæ', 'rec.exec.', 'pass', @id
go

create proc UpdatePersonel
	@PersonelTypeID int, @Username nvarchar(50), @FirstName nvarchar(50),
	@MiddleName nvarchar(50), @Surname nvarchar(50), @Title nvarchar(50)
as
begin
	if exists (select top 1 Username
		from Personnel
		where Valid=1
		and Username=@Username)
    begin
        update Personnel
		set PersonelTypeID=@PersonelTypeID,
			FirstName=@FirstName,
			MiddleName=@MiddleName,
			Surname=@Surname,
			Title=@Title
		where Username=@Username
	end
end
go

create proc AuthPersonel
	@Username nvarchar(50), @Password nvarchar(max), @Out int output
as
begin
	set nocount on
    declare @userId int

    if exists (select top 1 Username
		from Personnel
		where valid=1 and Username=@Username)
    begin
        set @userId=(select IDPersonel
			from Personnel
			where Valid=1
			and Username=@Username
			and PasswordHash=HASHBYTES('SHA2_512', @Password+CAST(Salt AS NVARCHAR(36))))

       if(@userId is null)
           set @Out=3	--lozinka neispravna
       else 
           set @Out=1	--uspjeh
    end
    else
       set @Out=2	--korisnicko ime ne postoji
end
go

create proc DeletePersonel
	@personelID int
as
begin
    delete from Personnel
	where IDPersonel=@personelID
end
go

create proc GetPersonelIdByUsernamePassword
	@Username nvarchar(50), @Password nvarchar(max), @Out int output
as
begin
	select @Out=IDPersonel
	from Personnel
	where Username=@Username
		and PasswordHash=HASHBYTES('SHA2_512', @Password+CAST(Salt AS NVARCHAR(36)))

	if (@Out is null) set @Out=-1
end
go


--patients
create proc FillMiniRegistrationForm
	@FirstName nvarchar(50),
	@MiddleName nvarchar(50),
	@Surname nvarchar(50),
	@Sex char(1),
	@DateOfBirth date,
	@ContactTelHome nvarchar(15),
	@ContactTelWork nvarchar(15),
	@NextOfKinFirstName nvarchar(50),
	@NextOfKinMiddleName nvarchar(50),
	@NextOfKinSurname nvarchar(50),
	@NextOfKinRelationshipToOutpatient nvarchar(50),
	@StatementOfComplaint nvarchar(200),
	@Out int output
as
begin
	begin try
		begin tran
			insert into Person values (@NextOfKinFirstName, @NextOfKinMiddleName, @NextOfKinSurname, null, null, null, null, null, null, null, null, null, null)
			declare @NextOfKin_PersonID int = scope_identity()

			insert into Person values (@FirstName, @MiddleName, @Surname, @Sex, @DateOfBirth, @ContactTelHome, @ContactTelWork, null, null, null, null, null, null)
			declare @PersonID int = scope_identity()

			insert into NextOfKin values (@NextOfKin_PersonID, @NextOfKinRelationshipToOutpatient)
			declare @NextOfKinID int = scope_identity()

			insert into Outpatient (PersonID, NextOfKinID, MaritalStatus, BloodTypeID, ComprehensiveFormFilled, NumberOfDependants, Height, Weight, Occupation, GrossAnnualIncome, Vegetarian, Smoker, AverageCigarettesPerDay, ConsumeAlcoholicBeverage, AverageDrinksPerDay, UseStimulants, Stimulants, CoffeOrTeaPerDay, SoftDrinksPerDay, HaveRegularMeals, PredominantlyEatsHome, StatementOfComplaint, HistoryOfPreviousTreatment, PhysicianHospitalTreated, Diabetic, Hypertensive, CardiacCondition, RaspiratoryCondition, DigestiveCondition, OrthopedicCondition, MuscularCondition, NeurologicalCondition, KnownAllergies, KnownAdverseReactionToSpecificDrugs, MayorSurgeriesHistory)
			values (@PersonID, @NextOfKinID, null, null, 0, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, @StatementOfComplaint, null, null, null, null, null, null, null, null, null, null, null, null, null)

			set @Out = scope_identity()

			insert into NewPatientDate
			values(GETDATE())
		commit
	end try
	begin catch
		set @Out = -1
		rollback
	end catch
end
go

create proc FillComprehensiveRegistrationForm
	@FirstName nvarchar(50),
	@MiddleName nvarchar(50),
	@Surname nvarchar(50),
	@Sex char(1),
	@DateOfBirth date,
	@TelephoneHome nvarchar(15),
	@TelephoneWork nvarchar(15),
	@Mobile nvarchar(15),
	@Pager nvarchar(15),
	@Fax nvarchar(15),
	@Email nvarchar(50),
	@PresentAddress nvarchar(200),
	@PermanentAddress nvarchar(200),
	@NextOfKinFirstName nvarchar(50),
	@NextOfKinMiddleName nvarchar(50),
	@NextOfKinSurname nvarchar(50),
	@NextOfKinRelationshipToOutpatient nvarchar(50),
	@NextOfKinAddress nvarchar(200),
	@NextOfKinTelephoneHome nvarchar(15),
	@NextOfKinTelephoneWork nvarchar(15),
	@NextOfKinMobile nvarchar(15),
	@NextOfKinPager nvarchar(15),
	@NextOfKinFax nvarchar(15),
	@NextOfKinEmail nvarchar(50),
	@MaritalStatus int,
	@BloodTypeID int,
	@ComprehensiveFormFilled bit,
	@NumberOfDependants int,
	@Height float,
	@Weight float,
	@Occupation nvarchar(50),
	@GrossAnnualIncome int,
	@Vegetarian bit,
	@Smoker bit,
	@AverageCigarettesPerDay int,
	@ConsumeAlcoholicBeverage bit,
	@AverageDrinksPerDay int,
	@UseStimulants bit,
	@Stimulants nvarchar(50),
	@CoffeOrTeaPerDay int,
	@SoftDrinksPerDay int,
	@HaveRegularMeals bit,
	@PredominantlyEatsHome bit,
	@StatementOfComplaint nvarchar(200),
	@HistoryOfPreviousTreatment nvarchar(200),
	@PhysicianHospitalTreated nvarchar(200),
	@Diabetic bit,
	@Hypertensive bit,
	@CardiacCondition bit,
	@RaspiratoryCondition bit,
	@DigestiveCondition bit,
	@OrthopedicCondition bit,
	@MuscularCondition bit,
	@NeurologicalCondition bit,
	@KnownAllergies nvarchar(200),
	@KnownAdverseReactionToSpecificDrugs nvarchar(200),
	@MayorSurgeriesHistory nvarchar(200),
	@Out int output
as
begin
	begin try
		begin tran
			insert into Person
			values (@NextOfKinFirstName, @NextOfKinMiddleName, @NextOfKinSurname, null, null, @NextOfKinTelephoneHome, @NextOfKinTelephoneWork, @NextOfKinMobile, @NextOfKinPager, @NextOfKinFax, @NextOfKinEmail, @NextOfKinAddress, null)
			declare @NextOfKin_PersonID int = scope_identity()

			insert into Person(FirstName, MiddleName, Surname, Sex, DateOfBirth, TelephoneHome, TelephoneWork, Mobile, Pager, Fax, Email, PresentAddress, PermanentAddress)
			values (@FirstName, @MiddleName, @Surname, @Sex, @DateOfBirth, @TelephoneHome, @TelephoneWork, @Mobile, @Pager, @Fax, @Email, @PresentAddress, @PermanentAddress)
			declare @PersonID int = scope_identity()

			insert into NextOfKin values (@NextOfKin_PersonID, @NextOfKinRelationshipToOutpatient)
			declare @NextOfKinID int = scope_identity()

			insert into Outpatient (PersonID, NextOfKinID, MaritalStatus, BloodTypeID, ComprehensiveFormFilled, NumberOfDependants, Height,
					Weight, Occupation, GrossAnnualIncome, Vegetarian, Smoker, AverageCigarettesPerDay, ConsumeAlcoholicBeverage, AverageDrinksPerDay,
					UseStimulants, Stimulants, CoffeOrTeaPerDay, SoftDrinksPerDay, HaveRegularMeals, PredominantlyEatsHome, StatementOfComplaint,
					HistoryOfPreviousTreatment, PhysicianHospitalTreated, Diabetic, Hypertensive, CardiacCondition, RaspiratoryCondition,
					DigestiveCondition, OrthopedicCondition, MuscularCondition, NeurologicalCondition, KnownAllergies,
					KnownAdverseReactionToSpecificDrugs, MayorSurgeriesHistory)
			values (@PersonID, @NextOfKinID, @MaritalStatus, @BloodTypeID, 1, @NumberOfDependants, @Height,
				@Weight, @Occupation, @GrossAnnualIncome, @Vegetarian, @Smoker, @AverageCigarettesPerDay, @ConsumeAlcoholicBeverage, @AverageDrinksPerDay,
				@UseStimulants, @Stimulants, @CoffeOrTeaPerDay, @SoftDrinksPerDay, @HaveRegularMeals, @PredominantlyEatsHome, @StatementOfComplaint,
				@HistoryOfPreviousTreatment, @PhysicianHospitalTreated, @Diabetic, @Hypertensive, @CardiacCondition, @RaspiratoryCondition,
				@DigestiveCondition, @OrthopedicCondition, @MuscularCondition, @NeurologicalCondition, @KnownAllergies,
				@KnownAdverseReactionToSpecificDrugs, @MayorSurgeriesHistory)

			set @Out = scope_identity()

			insert into NewPatientDate
			values(GETDATE())
		commit
	end try
	begin catch
		set @Out = -1
		rollback
	end catch
end
go

create proc GetPatientsDetails
	@idPatient int
as
begin
	select top 1 o.IDOutpatient, p.IDPerson, p.FirstName, p.MiddleName, p.Surname, p.Sex, p.DateOfBirth,
	p.TelephoneHome, p.TelephoneWork, p.Mobile, p.Pager, p.Fax, p.Email, p.PresentAddress, p.PermanentAddress,
		nok.FirstName as nokFirstName, nok.MiddleName as nokMiddleName, nok.Surname as nokSurname,
		nok.TelephoneHome as nokTelephoneHome, nok.TelephoneWork as nokTelephoneWork, nok.Mobile as nokMobile,
		nok.Email as nokEmail, nok.Pager as nokPager, nok.Fax as nokFax, nok.PermanentAddress as nokAddress,
		nxokn.RelationshipToOutpatient as nokRelationship, nok.IDPerson as nokIDPerson, o.BloodTypeID,
		o.MaritalStatus, o.ComprehensiveFormFilled, o.NumberOfDependants, o.Height, o.Weight, o.Occupation,
		o.GrossAnnualIncome, o.Vegetarian, o.Smoker, o.AverageCigarettesPerDay, o.ConsumeAlcoholicBeverage,
		o.AverageDrinksPerDay, o.UseStimulants, o.Stimulants, o.CoffeOrTeaPerDay, o.SoftDrinksPerDay,
		o.HaveRegularMeals, o.PredominantlyEatsHome, o.StatementOfComplaint, o.HistoryOfPreviousTreatment,
		o.PhysicianHospitalTreated, o.Diabetic, o.Hypertensive, o.CardiacCondition, o.RaspiratoryCondition,
		o.DigestiveCondition, o.OrthopedicCondition, o.MuscularCondition, o.NeurologicalCondition,
		o.KnownAllergies, o.KnownAdverseReactionToSpecificDrugs, o.MayorSurgeriesHistory
	from Outpatient as o
	left join Person as p
	on o.PersonID=p.IDPerson
	left join NextOfKin as nxokn
	on o.NextOfKinID=nxokn.IDNextOfKin
	left join Person as nok
	on nxokn.PersonID=nok.IDPerson
	where o.IDOutpatient=@idPatient
end
go

create proc GetPatientsList
as
begin
	select o.IDOutpatient, p.FirstName, p.MiddleName, p.Surname, p.Sex,
		p.DateOfBirth, p.TelephoneHome, p.TelephoneWork, p.Mobile,
		p.Pager, p.Fax, p.Email, p.PresentAddress, p.PermanentAddress
	from Outpatient as o
	left join Person as p
	on o.PersonID = p.IDPerson
end
go


--patients for doctor
create proc GetPatientsForDoctor
	@idDoctor int
as
begin
	select distinct o.IDOutpatient, p.FirstName, p.MiddleName, p.Surname, p.Sex,
		p.DateOfBirth, p.TelephoneHome, p.TelephoneWork, p.Mobile,
		p.Pager, p.Fax, p.Email, p.PresentAddress, p.PermanentAddress
	from Outpatient as o
	left join Person as p
	on o.PersonID = p.IDPerson
	left join Outpatient_Personel as op
	on op.OutpatientID=o.IDOutpatient
	where op.PersonelID=@idDoctor
end
go

create proc InsertPatientForDoctor
	@idOutpatient int, @idDoctor int
as
begin
	insert into Outpatient_Personel
	values (@idOutpatient, @idDoctor)
end
go


--events
create proc GetEventsForPatientDoctor
	@idOutpatient int, @idDoctor int
as
begin
	select IDEvent, OutpatientID, PersonelID, EventTypeID, AppointedDate, Comment
	from PersonelOutpatientEvent
	where OutpatientID=@idOutpatient
	and PersonelID=@idDoctor
	order by AppointedDate
end
go

create proc GetEventsForPatientForCharge
	@idPatient int
as
begin
	select IDEvent, OutpatientID, PersonelID, EventTypeID, AppointedDate, Comment
	from PersonelOutpatientEvent as e
	where e.OutpatientID=@idPatient
		and e.EventTypeID not in (select IDEventType from EventType where Name = 'Appointment')
		and e.IDEvent not in (select EventID from EventPayment)
	order by AppointedDate
end
go

create proc InsertEvent
	@idDoctor int, @idOutpatient int, @date date, @eventTypeID int, @comment nvarchar(200)
as
begin
	insert into PersonelOutpatientEvent
	values (@idOutpatient, @idDoctor, @eventTypeID, @date, @comment)
end
go

create proc ChargeEvent
	@idEvent int, @idPaymentType int, @amount float
as
begin
	insert into EventPayment
	values (@idEvent, @idPaymentType, @amount)
end
go


--event types
create proc GetEventTypes
as
begin
	select IDEventType, Name from EventType
end
go

create proc GetEventType
	@id int
as
begin
	select IDEventType, Name from EventType where IDEventType=@id
end
go


--personel types
create proc GetPersonelTypes
as
begin
	select IDPersonnelType, Name from PersonnelType
end
go

create proc GetPersonelType
	@id int
as
begin
	select IDPersonnelType, Name from PersonnelType where IDPersonnelType=@id
end
go


--blood types
create proc GetBloodTypes
as
begin
	select IDBloodType, Name from BloodType
end
go

create proc GetBloodType
	@id int
as
begin
	select IDBloodType, Name from BloodType where IDBloodType=@id
end
go


--marital statuses
create proc GetMaritalStatuses
as
begin
	select IDMaritalStatus, Name from MaritalStatus
end
go

create proc GetMaritalStatus
	@id int
as
begin
	select IDMaritalStatus, Name from MaritalStatus where IDMaritalStatus=@id
end
go