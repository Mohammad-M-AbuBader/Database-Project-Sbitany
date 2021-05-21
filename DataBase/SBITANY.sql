CREATE DATABASE SBITANY;

SHOW DATABASES;

USE SBITANY;

CREATE TABLE City(
	 cityID int NOT NULL,
	 cityName varchar(40) NOT NULL,
	 PRIMARY KEY (cityID)
)auto_increment = 1;



CREATE TABLE Customer(
	customerID int NOT NULL, 
	customerName varchar(40) NOT NULL,
	cardID long NOT NULL,
	cityID int NOT NULL,
    villageID int NOT NULL,
    streetName varchar(40),
    regionName varchar(40) ,
    bulldingNumber int,
    phone varchar(100),
    PRIMARY KEY (customerID),
	FOREIGN KEY (cityID) REFERENCES City(cityID),
    foreign key (villageID) references village(villageID)
);

CREATE TABLE MainStorge(
	mainStrogeID int NOT NULL, 
	mainStorageName varchar(40) NOT NULL,
	cityID int NOT NULL,
    villageID int not null,
     streetName varchar(40),
    regionName varchar(40) ,
    bulldingNumber int,
    PRIMARY KEY(mainStrogeID),
	FOREIGN KEY (cityID) REFERENCES City(cityID),
    foreign key (villageID) references village(villageID)
);


CREATE TABLE MainCompany(
	mainCompanyName varchar(40) NOT NULL, 
	companyPhone varchar(50),
	mainStorageID int NOT NULL,
    cityID int NOT NULL,
    villageId int not null,
    streetName varchar(40),
    regionName varchar(40) ,
    bulldingNumber int,
    PRIMARY KEY(mainCompanyName),
    FOREIGN KEY (cityID) REFERENCES City(cityID),
    foreign key (villageID) references village(villageID),
    foreign key(mainStorageID) references mainstorge(mainStorageID)
);

CREATE TABLE Branch(
	branchID int NOT NULL, 
	branchName varchar(40) NOT NULL,
	branchPhone varchar(50),
    mainCompanyName varchar(40) NOT NULL,
    cityID int NOT NULL,
    villageID int not null,
    streetName varchar(40),
    regionName varchar(40) ,
    bulldingNumber int,
    PRIMARY KEY(branchID),
	FOREIGN KEY(mainCompanyName) REFERENCES MainCompany(mainCompanyName),
	FOREIGN KEY (cityID) REFERENCES City(cityID),
    foreign key (villageID) references village(villageID)
);

insert into branch(branchName, branchPhone, cityID, regionName, streetName, bulldingNumber) values
("Sbitany Sale Nablus St", "02-123-0020",7 , "Ramallah", "Nablus St", 346),
("Sbitany Sale Al Hisbeh St", "02-123-0030",7 , "Ramallah", "Al Hisbeh St", 115),
("Sbitany Hitech", "02-123-0040",7 , "Ramallah", "Rukab St", 234),
("Sbitany IStore", "02-123-0050",7 , "Ramallah", "Rukab Street", 215),
("Sbitany Sale Jerusalem-Hebron St", "02-123-0060",1 , "Bethlehem", "Jerusalem-Hebron St", 287),
("Sbitany Al Mahed St", "02-123-0070",1 , "Bethlehem", "Al Mahed St", 164),
("Sbitany Al Madbaseh St", "02-123-0080",1 , "Bethlehem", "Al Madbaseh St", 152),
("Sbitany Sale Wadi Tufah St", "02-123-0090",2 , "Hebron", "Wadi Tufah St", 241),
("Sbitany Reseller", "02-123-0100",2 , "Hebron", "Sa'eer St", 175),
("Sbitany Beit Ula", "02-123-0110",2 , "Hebron_Beit Ula", "Khelit El-Jame St", 340),
("Sbitany Al-Dahriyeh St", "02-123-0120",2 , "Hebron", "Al-Dahriyeh St", 285),
("Sbitany Sale Hitteen St", "02-123-0130",5 , "Nablus", "Hitteen St", 361),
("Sbitany Sale Rafidia St", "02-123-0140",5 , "Nablus", "Rafidia St", 297),
("Sbitany Sale Rafidia", "02-123-0150",5 , "Nablus", "Rafidia St", 224),
("Sbitany Jenin", "02-123-0160",3 , "Jenin", "Main St", 143),
("Sbitany Jericho", "02-123-0170",4 , "Jericho", "Jerusalem St", 321),
("Sbitany Sale Al-Ram", "02-123-0180",11 , "Jerusalem Al-Ram", "Ramallah-Jerusalem St", 261),
("Sbitany Al-Ram", "02-123-0190",11 , "Jerusalem Al-Ram", "Ramallah St", 159);
select COUNT(*) from branch;
CREATE TABLE BranchStorge(
	bracnhStorgeID int NOT NULL,
	bracnhStorgeName varchar(40) NOT NULL,
    addressID int NOT NULL,
	PRIMARY KEY(bracnhStorgeID),
	FOREIGN KEY (addressID) REFERENCES Address(addressID)
)auto_increment = 101;


CREATE TABLE Categories(
	catogresId int NOT NULL,
	catogresName varchar(40) NOT NULL,
	PRIMARY KEY(catogresId)
);
SELECT DATE_FORMAT(FROM_DAYS(DATEDIFF(employee.employeeDateOfBirth,'2010-01-01')), ‘%Y’)+0 AS age;


CREATE TABLE Product(
	productCode int NOT NULL,
	productName varchar(40) NOT NULL,
	manufacturerCompany varchar(40) NOT NULL,
	purchasingPrice int NOT NULL,
	sellingPrice int NOT NULL,
	catogresId int NOT NULL,
	parCode long NOT NULL,
    descriptions varchar(100),
    PRIMARY KEY(productCode),
    FOREIGN KEY(catogresId) REFERENCES Catogres(catogresId)
);

CREATE TABLE JobTitle(
	jobTitleID int NOT NULL,
	jobName varchar(40) NOT NULL,
     PRIMARY KEY(jobTitleID)
);


CREATE TABLE Employee(
	employeeID int NOT NULL,
	employeeCard long NOT NULL,
	employeeName varchar(40) NOT NULL,
	employeePhone varchar(100),
	employeeDateOfBirth date NOT NULL,
	employeeSalary int NOT NULL,
	employeeEmail varchar(50),
	employeeUserName varchar(40),
	employeePassword varchar(40),
	employeeHiringDate date NOT NULL,
	employeeFiringDate date,
    jobTitleID int NOT NULL,
    cityID int NOT NULL,
    villageID int not null,
    mainCompanyName varchar(40) NOT NULL,
    branchID int NOT NULL, 
	PRIMARY KEY(employeeID),
	FOREIGN KEY (cityID) REFERENCES City(cityID),
    foreign key (villageID) references village(villageID),
	FOREIGN KEY(jobTitleID) REFERENCES JobTitle(jobTitleID),
	FOREIGN KEY(mainCompanyName) REFERENCES MainCompany(mainCompanyName),
	FOREIGN KEY(branchID) REFERENCES Branch(branchID)
);
insert into city(cityName) values 
("Jerusalem");

select * from city; 
select * from city;
CREATE TABLE BranchGetFrom(
	transferNumber int NOT NULL,
	getAt date NOT NULL,
    mainStorageID int,
	employeeID int NOT NULL,
    sourceBranchID int,
    destinationBranchID int NOT NULL ,
    productCode int NOT NULL,
    quantity int not null,
	PRIMARY KEY(transferNumber),
    FOREIGN KEY(employeeID) REFERENCES Employee(employeeID),
	FOREIGN KEY(mainStorageID) REFERENCES mainstorge(mainStorageID),
	FOREIGN KEY(productCode) REFERENCES Product(productCode),
    FOREIGN KEY(sourceBranchID) REFERENCES branch(branchID),
    FOREIGN KEY(destinationBranchID) REFERENCES branch(branchID)
);



CREATE TABLE sockedproducts(
	mainStorageID int NOT NULL,
	productCode int NOT NULL, 
	productQuantity int NOT NULL,
    PRIMARY KEY(mainStorageID, productCode),
    FOREIGN KEY(mainStorageID) REFERENCES MainStorge(mainStorageID),
    FOREIGN KEY(productCode) REFERENCES Product(productCode)
);


CREATE TABLE BranchDisplayProduct(
	branchID int NOT NULL, 
	productCode int NOT NULL,
	PRIMARY KEY(branchID, productCode),
    FOREIGN KEY(branchID) REFERENCES Branch(branchID),
    FOREIGN KEY(productCode) REFERENCES Product(productCode)
);


CREATE TABLE Supplier(
	supplierID int NOT NULL,
    supplierName varchar(40) NOT NULL,
	supplierPhone varchar(100),
    supplierEmail varchar(40),
    supplierFax varchar(40),
     PRIMARY KEY(supplierID)
);


CREATE TABLE customerBill(
	customerBillID int NOT NULL,
	orederAt date NOT NULL,
    valueOfBill int NOT NULL,
	customerID int NOT NULL, 
	branchID int NOT NULL, 
	employeeID int NOT NULL,
	deposit int ,
	patches int,
	PRIMARY KEY(customerBillID),
	FOREIGN KEY(customerID) REFERENCES Customer(customerID),
	FOREIGN KEY(branchID) REFERENCES Branch(branchID),
	FOREIGN KEY(employeeID) REFERENCES Employee(employeeID)
);


CREATE TABLE CustomerBillDetails(
	customerBillID int NOT NULL,
	productCode int NOT NULL, 
	sellingPrice int NOT NULL,
	quantity int NOT NULL,
	PRIMARY KEY(customerBillID, productCode),
    FOREIGN KEY(customerBillID) REFERENCES customerbill(customerBillID),
    FOREIGN KEY(productCode) REFERENCES Product(productCode)
);


CREATE TABLE SupplierBill(
	SupplierBillID int NOT NULL,
	orederAt date NOT NULL,
	mainCompanyName varchar(40) NOT NULL,
	supplierID int NOT NULL, 
	valueOfBill int NOT NULL,
	deposit int ,
	patches int,
	PRIMARY KEY(SupplierBillID),
    FOREIGN KEY(mainCompanyName) REFERENCES MainCompany(mainCompanyName),
    FOREIGN KEY(supplierID) REFERENCES Supplier(supplierID)
);


CREATE TABLE SupplierBillDetails(
	SupplierBillID int NOT NULL,
	productCode int NOT NULL,
    purchasingPrice int NOT NULL,
	quantity int NOT NULL,
    PRIMARY KEY(SupplierBillID, productCode),
    FOREIGN KEY(SupplierBillID) REFERENCES supplierbill(SupplierBillID),
    FOREIGN KEY(productCode) REFERENCES Product(productCode)
);
