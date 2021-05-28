create table Brands_Models (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	brand VARCHAR(50) NOT NULL,
	model VARCHAR(50) NOT NULL,
	model_year VARCHAR(50)
);

create table Types (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

create table Purposes (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

create table Vehicles (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	vin VARCHAR(50) NOT NULL UNIQUE,
	plates VARCHAR(50) NOT NULL UNIQUE,
	equipmentLevel VARCHAR(8) NOT NULL,
	mileage INT NOT NULL,
	avgFuelConsumption DECIMAL(4,2) NOT NULL,
	Brands_Models_id INT NOT NULL,
	Types_id INT NOT NULL,
	Purposes_id INT NOT NULL,
    CONSTRAINT Vehicles_Brands_Models_FK FOREIGN KEY ( Brands_Models_id ) REFERENCES Brands_Models ( id ),
    CONSTRAINT Vehicles_Types_FK FOREIGN KEY ( Types_id ) REFERENCES Types ( id ),
    CONSTRAINT Vehicles_Purposes_FK FOREIGN KEY ( Purposes_id ) REFERENCES Purposes ( id )
);

create table Functions (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

create table People (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	firstName VARCHAR(50) NOT NULL,
	lastName VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(50) NOT NULL,
	mail VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(64) NOT NULL,
	Functions_id INT NOT NULL,
    CONSTRAINT People_Functions_FK FOREIGN KEY ( Functions_id ) REFERENCES Functions ( id )
);

create table Keeping (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	startDate DATE NOT NULL,
	endDate DATE,
	People_id INT NOT NULL,
	Vehicles_id INT NOT NULL,
    CONSTRAINT Keeping_Vehicles_FK FOREIGN KEY ( Vehicles_id ) REFERENCES Vehicles ( id ),
    CONSTRAINT Keeping_People_FK FOREIGN KEY ( People_id ) REFERENCES People ( id )
);

create table Subcontractors (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	address VARCHAR(50) NOT NULL,
	phoneNumber VARCHAR(50) NOT NULL
);

create table Service_types (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

create table Vehicle_unavailability (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	business VARCHAR(1) NOT NULL,
	Vehicles_id INT NOT NULL,
	People_id INT NOT NULL,
    CONSTRAINT Vehicle_unavailability_People_FK FOREIGN KEY ( People_id ) REFERENCES People ( id ),
    CONSTRAINT Vehicle_unavailability_Vehicles_FK FOREIGN KEY ( Vehicles_id ) REFERENCES Vehicles ( id )
);

create table Service_request (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	date DATE NOT NULL,
	description VARCHAR(100) NOT NULL,
    Service_types_id INT NOT NULL,
    Vehicle_unavailability_id INT NOT NULL,
    CONSTRAINT Service_request_Vehicle_unavailability_FK FOREIGN KEY ( Vehicle_unavailability_id ) REFERENCES Vehicle_unavailability ( id ),
    CONSTRAINT Service_request_Service_types_FK FOREIGN KEY ( Service_types_id ) REFERENCES Service_types ( id )
);

create table Servicing (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	price DECIMAL(8,2) NOT NULL,
	Subcontractors_id INT NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	isFinished VARCHAR(1) NOT NULL,
	description VARCHAR(100) NOT NULL,
    Service_request_id INT NOT NULL,
    CONSTRAINT Servicing_Subcontractors_FK FOREIGN KEY ( Subcontractors_id ) REFERENCES Subcontractors ( id ),
    CONSTRAINT Servicing_Service_request_FK FOREIGN KEY ( Service_request_id ) REFERENCES Service_request ( id )
);

create table Operation_type (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL
);

create table Vehicle_rentings (
    id BIGSERIAL NOT NULL PRIMARY KEY,
	startMileage INT NOT NULL,
	endMileage INT NOT NULL,
    startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	isBusiness VARCHAR(1) NOT NULL,
    Vehicle_unavailability_id INT NOT NULL,
    CONSTRAINT Vehicle_rentings_Vehicle_unavailability_FK FOREIGN KEY ( Vehicle_unavailability_id ) REFERENCES Vehicle_unavailability ( id )
);

create table Operation_costs (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    date DATE NOT NULL,
	price DECIMAL(8,2) NOT NULL,
	description VARCHAR(100) NOT NULL,
	Vehicles_id INT NOT NULL,
	Operation_type_id INT NOT NULL,
	Vehicle_rentings_id INT,
	Keeping_id INT NOT NULL,
    CONSTRAINT Operation_costs_Vehicles_FK FOREIGN KEY ( Vehicles_id ) REFERENCES Vehicles ( id ),
    CONSTRAINT Operation_costs_Keeping_FK FOREIGN KEY ( Keeping_id ) REFERENCES Keeping ( id ),
    CONSTRAINT Operation_costs_Operation_type_FK FOREIGN KEY ( Operation_type_id ) REFERENCES Operation_type ( id ),
    CONSTRAINT Operation_costs_Vehicle_rentings_FK FOREIGN KEY ( Vehicle_rentings_id ) REFERENCES Vehicle_rentings ( id )
);


insert into Purposes (name) values ('universal');
insert into Purposes (name) values ('furniture transport');
insert into Purposes (name) values ('carriage of bread');
insert into Purposes (name) values ('postage');
insert into Purposes (name) values ('other');
insert into Purposes (name) values ('car');
insert into Purposes (name) values ('passenger transportation');
insert into Purposes (name) values ('carriage of vehicles');
insert into Purposes (name) values ('waste disposal');
insert into Purposes (name) values ('carriage of other cargo');
insert into Purposes (name) values ('carriage of liquid fuels');
insert into Purposes (name) values ('carriage of gases');
insert into Purposes (name) values ('concrete mixer');
insert into Purposes (name) values ('carriage of live poultry');
insert into Purposes (name) values ('carriage of beverages');


insert into Types (name) values ('Sedan');
insert into Types (name) values ('Hatchback');
insert into Types (name) values ('Liftback');
insert into Types (name) values ('MPV');
insert into Types (name) values ('VAN');
insert into Types (name) values ('SUV');
insert into Types (name) values ('Crossover');
insert into Types (name) values ('Coupe');
insert into Types (name) values ('Convertible');
insert into Types (name) values ('Pickup');

insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Silverado', 2002);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', '3500', 1994);
insert into Brands_Models (brand, model, model_year) values ('Saab', '900', 1989);
insert into Brands_Models (brand, model, model_year) values ('Maybach', '57', 2005);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Mirage', 1995);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Crown Victoria', 2006);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'E-Class', 1999);
insert into Brands_Models (brand, model, model_year) values ('Maserati', 'Spyder', 2005);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'Familia', 1986);
insert into Brands_Models (brand, model, model_year) values ('Kia', 'Amanti', 2006);
insert into Brands_Models (brand, model, model_year) values ('Nissan', '240SX', 1993);
insert into Brands_Models (brand, model, model_year) values ('Volkswagen', 'Corrado', 1994);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Outlander Sport', 2011);
insert into Brands_Models (brand, model, model_year) values ('GMC', '2500 Club Coupe', 1992);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Diamante', 1999);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Sierra 3500', 2005);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'M-Class', 2010);
insert into Brands_Models (brand, model, model_year) values ('Nissan', 'Sentra', 1993);
insert into Brands_Models (brand, model, model_year) values ('Hummer', 'H3T', 2009);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'GTO', 1971);
insert into Brands_Models (brand, model, model_year) values ('Jaguar', 'XK', 2011);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Yaris', 2009);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Express 3500', 2011);
insert into Brands_Models (brand, model, model_year) values ('Lamborghini', 'Diablo', 1993);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Avalanche 1500', 2003);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'Mazda5', 2009);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'Electra', 1989);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Sierra 3500', 2005);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'MPV', 2000);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', '2500', 1999);
insert into Brands_Models (brand, model, model_year) values ('Plymouth', 'Sundance', 1994);
insert into Brands_Models (brand, model, model_year) values ('Volvo', '940', 1993);
insert into Brands_Models (brand, model, model_year) values ('Volkswagen', 'riolet', 1992);
insert into Brands_Models (brand, model, model_year) values ('BMW', 'X5', 2004);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'TrailBlazer', 2009);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Dynasty', 1992);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'XT', 1991);
insert into Brands_Models (brand, model, model_year) values ('MINI', 'Clubman', 2008);
insert into Brands_Models (brand, model, model_year) values ('Honda', 'S2000', 2002);
insert into Brands_Models (brand, model, model_year) values ('Porsche', 'Cayenne', 2010);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Intrepid', 2001);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'GTO', 1972);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Sierra 1500', 2012);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'C-Class', 1998);
insert into Brands_Models (brand, model, model_year) values ('Cadillac', 'Seville', 1995);
insert into Brands_Models (brand, model, model_year) values ('Honda', 'Pilot', 2005);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Tacoma', 2003);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Grand Am', 2004);
insert into Brands_Models (brand, model, model_year) values ('Scion', 'tC', 2005);
insert into Brands_Models (brand, model, model_year) values ('Nissan', 'Altima', 2009);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'Justy', 1994);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Sierra 2500', 2012);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'MX-5', 2008);
insert into Brands_Models (brand, model, model_year) values ('Audi', '90', 1989);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'W201', 1984);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Astro', 2003);
insert into Brands_Models (brand, model, model_year) values ('Rolls-Royce', 'Phantom', 2009);
insert into Brands_Models (brand, model, model_year) values ('Land Rover', 'Range Rover', 1998);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'G-Series G10', 1993);
insert into Brands_Models (brand, model, model_year) values ('Eagle', 'Talon', 1994);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Blazer', 1994);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'Regal', 1986);
insert into Brands_Models (brand, model, model_year) values ('Rolls-Royce', 'Phantom', 2006);
insert into Brands_Models (brand, model, model_year) values ('GMC', '3500', 1994);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Mustang', 2007);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Topaz', 1989);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Camry', 2000);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Firefly', 1985);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'Enclave', 2012);
insert into Brands_Models (brand, model, model_year) values ('Saab', '9-3', 2008);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Ram 2500', 1997);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Pajero', 2003);
insert into Brands_Models (brand, model, model_year) values ('GMC', '3500', 1995);
insert into Brands_Models (brand, model, model_year) values ('Land Rover', 'LR4', 2012);
insert into Brands_Models (brand, model, model_year) values ('Jeep', 'Wrangler', 2010);
insert into Brands_Models (brand, model, model_year) values ('BMW', 'Z3', 1998);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Montana', 2000);
insert into Brands_Models (brand, model, model_year) values ('Acura', 'RSX', 2006);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Ram 2500', 2001);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'M-Class', 2005);
insert into Brands_Models (brand, model, model_year) values ('Lamborghini', 'Diablo', 1991);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Mustang', 1990);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'RX-8', 2006);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Ranger', 2009);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Xtra', 1995);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Envoy', 2006);
insert into Brands_Models (brand, model, model_year) values ('Maserati', 'Karif', 1989);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'TundraMax', 2007);
insert into Brands_Models (brand, model, model_year) values ('Audi', 'Allroad', 2004);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Aveo', 2008);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Endeavor', 2004);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Daytona', 1992);
insert into Brands_Models (brand, model, model_year) values ('Hummer', 'H3', 2010);
insert into Brands_Models (brand, model, model_year) values ('Chrysler', 'Town & Country', 2000);
insert into Brands_Models (brand, model, model_year) values ('BMW', '3 Series', 1992);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Capri', 1994);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'Legacy', 2002);
insert into Brands_Models (brand, model, model_year) values ('Saturn', 'VUE', 2006);
insert into Brands_Models (brand, model, model_year) values ('Lexus', 'ES', 2004);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'FJ Cruiser', 2007);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'LeSabre', 2003);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Yukon XL 1500', 2005);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'RX-7', 1988);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'FJ Cruiser', 2012);
insert into Brands_Models (brand, model, model_year) values ('BMW', 'Z4', 2003);
insert into Brands_Models (brand, model, model_year) values ('BMW', '7 Series', 2011);
insert into Brands_Models (brand, model, model_year) values ('Jaguar', 'S-Type', 2000);
insert into Brands_Models (brand, model, model_year) values ('Cadillac', 'DeVille', 1999);
insert into Brands_Models (brand, model, model_year) values ('Jeep', 'Liberty', 2003);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'LTD Crown Victoria', 1990);
insert into Brands_Models (brand, model, model_year) values ('BMW', '530', 2003);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'D350', 1992);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'RX-8', 2010);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Ram 1500', 1995);
insert into Brands_Models (brand, model, model_year) values ('Porsche', '911', 1998);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Explorer', 2004);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Sable', 1998);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'S-Class', 1998);
insert into Brands_Models (brand, model, model_year) values ('Aston Martin', 'DBS', 2008);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Sable', 1995);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Sunbird', 1993);
insert into Brands_Models (brand, model, model_year) values ('Volkswagen', 'Fox', 1989);
insert into Brands_Models (brand, model, model_year) values ('Lincoln', 'Continental', 1988);
insert into Brands_Models (brand, model, model_year) values ('Porsche', '928', 1986);
insert into Brands_Models (brand, model, model_year) values ('Isuzu', 'Rodeo', 1993);
insert into Brands_Models (brand, model, model_year) values ('BMW', '760', 2005);
insert into Brands_Models (brand, model, model_year) values ('Saab', '9-3', 2005);
insert into Brands_Models (brand, model, model_year) values ('Volvo', 'V90', 1998);
insert into Brands_Models (brand, model, model_year) values ('Morgan', 'Aero 8', 2008);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Grand Prix', 1966);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'Impreza', 1997);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', '500SEL', 1992);
insert into Brands_Models (brand, model, model_year) values ('Mazda', '929', 1992);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Dakota Club', 1996);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'CL-Class', 2011);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'Miata MX-5', 1992);
insert into Brands_Models (brand, model, model_year) values ('Lamborghini', 'Gallardo', 2008);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Ranger', 1987);
insert into Brands_Models (brand, model, model_year) values ('BMW', 'M3', 1996);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Chariot', 1989);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'LeSabre', 2005);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Rally Wagon G3500', 1996);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Sienna', 2001);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Venza', 2009);
insert into Brands_Models (brand, model, model_year) values ('Dodge', 'Caravan', 1998);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Cavalier', 2002);
insert into Brands_Models (brand, model, model_year) values ('Audi', '100', 1989);
insert into Brands_Models (brand, model, model_year) values ('Lexus', 'RX', 2008);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Escape', 2011);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Truck', 1994);
insert into Brands_Models (brand, model, model_year) values ('Cadillac', 'DeVille', 1994);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', '300TE', 1992);
insert into Brands_Models (brand, model, model_year) values ('Bentley', 'Continental', 2012);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Acadia', 2008);
insert into Brands_Models (brand, model, model_year) values ('Pontiac', 'Grand Prix', 1978);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Express 1500', 1996);
insert into Brands_Models (brand, model, model_year) values ('Bentley', 'Arnage', 2006);
insert into Brands_Models (brand, model, model_year) values ('Hyundai', 'Elantra', 1995);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'CLK-Class', 2003);
insert into Brands_Models (brand, model, model_year) values ('Hyundai', 'Azera', 2007);
insert into Brands_Models (brand, model, model_year) values ('Jeep', 'Wrangler', 2010);
insert into Brands_Models (brand, model, model_year) values ('Lexus', 'ES', 1992);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Corolla', 2010);
insert into Brands_Models (brand, model, model_year) values ('Lotus', 'Exige', 2007);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Camry', 1994);
insert into Brands_Models (brand, model, model_year) values ('GMC', 'Jimmy', 1995);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Lancer', 2012);
insert into Brands_Models (brand, model, model_year) values ('Mazda', 'MX-5', 2001);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Tacoma', 2009);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Mustang', 1967);
insert into Brands_Models (brand, model, model_year) values ('Honda', 'Insight', 2012);
insert into Brands_Models (brand, model, model_year) values ('Acura', 'TL', 2009);
insert into Brands_Models (brand, model, model_year) values ('Bentley', 'Azure', 2008);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Uplander', 2007);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Highlander', 2006);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Equinox', 2006);
insert into Brands_Models (brand, model, model_year) values ('Buick', 'Terraza', 2005);
insert into Brands_Models (brand, model, model_year) values ('Cadillac', 'Sixty Special', 1993);
insert into Brands_Models (brand, model, model_year) values ('Mitsubishi', 'Montero', 1993);
insert into Brands_Models (brand, model, model_year) values ('Mercedes-Benz', 'E-Class', 1987);
insert into Brands_Models (brand, model, model_year) values ('Daewoo', 'Leganza', 2002);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'Mustang', 2000);
insert into Brands_Models (brand, model, model_year) values ('Honda', 'Pilot', 2009);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Monte Carlo', 2005);
insert into Brands_Models (brand, model, model_year) values ('Aston Martin', 'Vantage', 2008);
insert into Brands_Models (brand, model, model_year) values ('Nissan', 'Versa', 2009);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Echo', 2003);
insert into Brands_Models (brand, model, model_year) values ('Nissan', 'NV3500', 2012);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'Impreza', 2009);
insert into Brands_Models (brand, model, model_year) values ('Chevrolet', 'Corvette', 1999);
insert into Brands_Models (brand, model, model_year) values ('Volkswagen', 'Golf', 1999);
insert into Brands_Models (brand, model, model_year) values ('Volvo', 'C30', 2013);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Mystique', 1999);
insert into Brands_Models (brand, model, model_year) values ('Land Rover', 'LR3', 2009);
insert into Brands_Models (brand, model, model_year) values ('Mercury', 'Mountaineer', 2005);
insert into Brands_Models (brand, model, model_year) values ('Suzuki', 'SJ 410', 1984);
insert into Brands_Models (brand, model, model_year) values ('Subaru', 'Legacy', 2011);
insert into Brands_Models (brand, model, model_year) values ('Ford', 'LTD Crown Victoria', 1987);
insert into Brands_Models (brand, model, model_year) values ('Toyota', 'Sienna', 2006);
insert into Brands_Models (brand, model, model_year) values ('Jeep', 'Liberty', 2011);


insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3BEF3DR636934', 'SK 00001', 'budget', 257128, 6.3, 50, 8, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19XFB2E50DE904562', 'SK 00002', 'standard', 284294, 12.87, 194, 9, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUKH78E58A298493', 'SK 00003', 'premium', 18387, 7.6, 30, 3, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KMHTC6AD2EU749158', 'SK 00004', 'premium', 386293, 5.74, 80, 3, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1C6RD7LP1CS569940', 'SK 00005', 'standard', 135210, 2.47, 80, 9, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDF98E35A908669', 'SK 00006', 'premium', 270824, 5.95, 50, 9, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WA1LMBFE4AD715366', 'SK 00007', 'budget', 358975, 6.77, 108, 9, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3CEF1ER072496', 'SK 00008', 'budget', 208736, 6.3, 191, 3, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JM1NC2LF5C0900325', 'SK 00009', 'premium', 192656, 6.68, 159, 2, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTEW1CM2EK063876', 'SK 00010', 'budget', 426666, 10.25, 133, 4, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6AK5SX2D0610559', 'SK 00011', 'premium', 167490, 2.28, 84, 4, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYUCDEF1AR390207', 'SK 00012', 'budget', 490408, 14.54, 65, 8, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2HKRM3H31CH147415', 'SK 00013', 'budget', 18852, 7.47, 93, 6, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN1AZ4EH2AM526515', 'SK 00014', 'premium', 457244, 3.41, 110, 6, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2G4WB52K831806589', 'SK 00015', 'premium', 116496, 9.36, 145, 10, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAWR33599P241078', 'SK 00016', 'premium', 460596, 2.06, 36, 3, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3D4PH9FV6AT670366', 'SK 00017', 'standard', 448242, 5.78, 88, 4, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5TFCW5F13CX009733', 'SK 00018', 'standard', 403820, 8.39, 67, 3, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFK66N25G514228', 'SK 00019', 'budget', 80718, 10.77, 143, 1, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBSWD9C57AP230839', 'SK 00020', 'standard', 202607, 8.06, 122, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4JGDA2EB3CA993976', 'SK 00021', 'standard', 101914, 9.04, 185, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA56643A463525', 'SK 00022', 'budget', 74038, 5.64, 65, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5J6TF2H51FL311106', 'SK 00023', 'budget', 473664, 4.2, 84, 5, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2C3CA6CT7AH118373', 'SK 00024', 'standard', 291977, 9.64, 102, 10, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAFU7C59BD009965', 'SK 00025', 'premium', 196198, 9.27, 147, 9, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBSPM9C55AE481505', 'SK 00026', 'budget', 297398, 3.25, 169, 10, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAAW33461E344551', 'SK 00027', 'budget', 242268, 14.08, 141, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1D7CW7GP2AS772566', 'SK 00028', 'budget', 315023, 12.47, 156, 2, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DE8E58D0890696', 'SK 00029', 'budget', 46992, 13.62, 37, 7, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WUAW2BFC9EN146587', 'SK 00030', 'standard', 417874, 13.66, 134, 10, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1YVHZ8BH1C5727918', 'SK 00031', 'premium', 420356, 11.2, 15, 7, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTNF1E80AK805797', 'SK 00032', 'standard', 113075, 10.0, 6, 4, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTEX1CM1BF561780', 'SK 00033', 'standard', 36093, 3.53, 103, 9, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1AB7AP0FY794521', 'SK 00034', 'budget', 255696, 14.25, 23, 10, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTEW1CM7EF307563', 'SK 00035', 'budget', 51905, 14.07, 193, 4, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3CZRE3H35BG733159', 'SK 00036', 'standard', 109373, 10.9, 117, 1, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAEJ13402A936170', 'SK 00037', 'standard', 393646, 14.77, 122, 9, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GD220CG1CZ917620', 'SK 00038', 'standard', 270367, 10.42, 50, 4, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYFC262X9R158015', 'SK 00039', 'premium', 218504, 4.29, 14, 7, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAULC68E74A232812', 'SK 00040', 'budget', 217730, 11.64, 97, 4, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV4852CZ0A1795294', 'SK 00041', 'premium', 121082, 6.41, 150, 8, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFNKE65BS049334', 'SK 00042', 'premium', 209504, 7.08, 178, 4, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAWR33557P520976', 'SK 00043', 'budget', 117628, 9.46, 156, 8, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1C4RDHDG2FC250817', 'SK 00044', 'budget', 168335, 10.58, 98, 6, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUHF98P36A078263', 'SK 00045', 'premium', 36324, 5.0, 48, 9, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SCBFT7ZA2EC918041', 'SK 00046', 'budget', 434316, 6.71, 31, 10, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KNDPB3A2XB7183928', 'SK 00047', 'premium', 30339, 9.32, 52, 4, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKS1CKJ2FR304243', 'SK 00048', 'standard', 141660, 11.06, 110, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3C1C57CA193809', 'SK 00049', 'premium', 219639, 6.35, 101, 8, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KL4CJCSB0DB925392', 'SK 00050', 'standard', 301173, 10.42, 121, 3, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDL74F05N217054', 'SK 00051', 'budget', 202866, 12.96, 82, 1, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2HNYD18706H921731', 'SK 00052', 'standard', 265712, 10.96, 145, 2, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5FNYF3H26AB843941', 'SK 00053', 'standard', 420624, 8.56, 198, 1, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN1CV6EK8EM123057', 'SK 00054', 'budget', 340147, 10.87, 4, 7, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SALAB2D41AA015179', 'SK 00055', 'standard', 235826, 3.38, 72, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA56993A383163', 'SK 00056', 'standard', 358914, 11.26, 196, 4, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDL64F85N539614', 'SK 00057', 'budget', 369143, 10.15, 3, 8, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTHDU1EF8C5544003', 'SK 00058', 'premium', 465422, 11.57, 187, 3, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUEV74F28N072845', 'SK 00059', 'standard', 211327, 7.67, 111, 10, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1C3CDZCB6CN394165', 'SK 00060', 'standard', 269321, 10.06, 128, 2, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GD020CG3CF825577', 'SK 00061', 'standard', 216000, 14.47, 94, 9, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUNF98P96A488231', 'SK 00062', 'standard', 227, 2.64, 19, 7, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SAJWA1EK7EM353649', 'SK 00063', 'premium', 357534, 2.33, 166, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3C4PDCAB1DT474553', 'SK 00064', 'standard', 121764, 9.32, 181, 7, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBSBL93423J323735', 'SK 00065', 'standard', 380157, 6.83, 125, 2, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4JGDA2EB3EA750378', 'SK 00066', 'premium', 460451, 7.53, 108, 5, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2FMGK5B84FB734312', 'SK 00067', 'budget', 328272, 7.39, 156, 4, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV140MAM6F1637420', 'SK 00068', 'premium', 267478, 5.96, 86, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SCBLE37G26C963804', 'SK 00069', 'standard', 457759, 9.27, 192, 4, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFNAE38DS902425', 'SK 00070', 'budget', 302464, 13.53, 86, 8, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAURVAFA4AN545358', 'SK 00071', 'standard', 191273, 11.73, 164, 1, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAWC73518E920149', 'SK 00072', 'standard', 353774, 2.23, 112, 2, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3D73M4HL8AG413763', 'SK 00073', 'budget', 416307, 7.1, 149, 5, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA9E5XEA742819', 'SK 00074', 'premium', 176999, 4.52, 196, 10, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DE8EY4B0846962', 'SK 00075', 'premium', 432620, 4.27, 109, 1, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTHBL1EF8E5607369', 'SK 00076', 'premium', 249315, 10.37, 174, 9, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6AZ5S35E0004490', 'SK 00077', 'budget', 17399, 3.93, 146, 2, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5UMDU93466L519856', 'SK 00078', 'premium', 116883, 11.3, 14, 8, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTMF1E81AK887549', 'SK 00079', 'standard', 358344, 10.06, 146, 9, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6YV36A565137441', 'SK 00080', 'premium', 431707, 11.77, 80, 9, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3G7C59FK304737', 'SK 00081', 'standard', 17539, 9.29, 196, 6, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WVWAN7AN1DE951728', 'SK 00082', 'premium', 195925, 9.76, 143, 2, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2G61M5S35F9253880', 'SK 00083', 'standard', 330243, 13.04, 138, 10, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAEV33444P859626', 'SK 00084', 'premium', 79207, 9.24, 63, 9, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DJ8EG3A0453021', 'SK 00085', 'budget', 27129, 10.41, 67, 5, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4T1BK1EB8FU370760', 'SK 00086', 'budget', 84349, 3.05, 124, 1, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3V7C55FP705386', 'SK 00087', 'standard', 471816, 11.21, 96, 1, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4JGBF2FE5BA528146', 'SK 00088', 'budget', 236753, 10.89, 14, 7, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G4GH5E35CF486647', 'SK 00089', 'standard', 180681, 9.47, 132, 4, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUFEAFM6BA124926', 'SK 00090', 'premium', 144635, 10.38, 81, 9, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUEF98E16A666146', 'SK 00091', 'budget', 32050, 6.52, 94, 7, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKS1KE05CR112310', 'SK 00092', 'standard', 16720, 9.65, 124, 2, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUCFAFR6FA925227', 'SK 00093', 'budget', 383424, 14.03, 145, 4, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA8F20AA051078', 'SK 00094', 'standard', 89999, 3.18, 69, 7, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUHGAFC3FN035291', 'SK 00095', 'budget', 64088, 6.08, 169, 4, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6AH5RX1F0919799', 'SK 00096', 'premium', 456011, 3.93, 32, 4, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBSWL935X9P998717', 'SK 00097', 'premium', 222033, 9.16, 62, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3VWKZ7AJ0BM116097', 'SK 00098', 'premium', 273745, 11.33, 174, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1AB7AP9FL774104', 'SK 00099', 'budget', 122542, 14.87, 83, 5, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3TMJU4GN1AM581928', 'SK 00100', 'premium', 489788, 14.23, 30, 3, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SALME1D42AA263830', 'SK 00101', 'standard', 64489, 3.58, 89, 8, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('TRUTC28N541313227', 'SK 00102', 'budget', 245500, 6.11, 62, 10, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYVKLEFXAG261576', 'SK 00103', 'premium', 493689, 14.91, 149, 10, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDGBFL6CA729660', 'SK 00104', 'standard', 403461, 8.82, 103, 10, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAYE8C53FD480267', 'SK 00105', 'premium', 330892, 13.69, 123, 3, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKUCEEF6AR191900', 'SK 00106', 'premium', 333168, 9.24, 67, 2, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3VW4S7AT7EM541206', 'SK 00107', 'standard', 446315, 8.36, 16, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2B3CJ5DT1BH005879', 'SK 00108', 'premium', 261962, 13.31, 86, 1, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WUADUAFG9F7409732', 'SK 00109', 'budget', 3134, 5.67, 33, 6, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3HKJ4FR421304', 'SK 00110', 'standard', 22213, 2.76, 7, 2, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUR2AFD1EN185412', 'SK 00111', 'premium', 392287, 13.48, 134, 3, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G4GF5E39CF358952', 'SK 00112', 'standard', 55637, 3.33, 116, 3, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SAJWA4EC0FM879424', 'SK 00113', 'standard', 224164, 2.39, 121, 4, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('VNKKTUD34FA758851', 'SK 00114', 'premium', 162599, 4.14, 193, 7, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3CEF4DR909210', 'SK 00115', 'standard', 208294, 3.01, 182, 10, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SCBDC4BL9AC168262', 'SK 00116', 'standard', 245798, 8.76, 104, 6, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FAHP3HN3AW233813', 'SK 00117', 'budget', 430773, 2.94, 25, 10, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5UXFG8C58AL162565', 'SK 00118', 'standard', 94603, 8.37, 15, 9, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4A31K5DF6CE170538', 'SK 00119', 'standard', 143542, 1.45, 70, 3, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WVWAA7AJ3CW871106', 'SK 00120', 'budget', 323424, 2.26, 95, 6, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5FRYD4H47GB944927', 'SK 00121', 'budget', 410672, 8.62, 96, 6, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN1CV6FE7AM421490', 'SK 00122', 'budget', 497985, 14.74, 81, 7, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19VDE3F32DE147917', 'SK 00123', 'standard', 409167, 8.17, 48, 3, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5GAKVBKDXFJ903380', 'SK 00124', 'standard', 497822, 7.91, 142, 5, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5FPYK1F43CB987638', 'SK 00125', 'standard', 364132, 10.7, 64, 6, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUGL98E56A434814', 'SK 00126', 'budget', 324948, 4.01, 30, 4, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1CN7AP1FL599685', 'SK 00127', 'budget', 130031, 11.71, 182, 4, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBABN53472J381376', 'SK 00128', 'premium', 413075, 12.03, 88, 8, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYUCGEF4AR052964', 'SK 00129', 'standard', 93541, 8.28, 30, 7, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS4CEF7DR867760', 'SK 00130', 'standard', 324733, 6.91, 6, 10, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUMGBFL9DA255088', 'SK 00131', 'standard', 93109, 13.04, 157, 7, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DC1E39D0023173', 'SK 00132', 'budget', 446889, 7.42, 94, 3, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SAJWA4DC6DM972558', 'SK 00133', 'premium', 269264, 7.82, 20, 8, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KNAGM4AD7D5738363', 'SK 00134', 'premium', 426903, 12.34, 100, 1, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DK8E31D0302497', 'SK 00135', 'premium', 336063, 7.34, 170, 2, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GTU1YEJ5DG809039', 'SK 00136', 'standard', 79129, 6.52, 163, 3, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAPH735X9E420305', 'SK 00137', 'budget', 221623, 3.62, 127, 9, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3C63DRBL8CG326455', 'SK 00138', 'premium', 479063, 13.99, 5, 5, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3C63D3CL9CG879305', 'SK 00139', 'premium', 431196, 5.86, 22, 6, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYEK62N44G002230', 'SK 00140', 'standard', 318332, 14.88, 152, 10, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3VWKP7AJ7EM706758', 'SK 00141', 'standard', 358519, 9.02, 34, 1, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1CN7AP1EK433817', 'SK 00142', 'budget', 256056, 11.94, 83, 5, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKS1CE0XER571082', 'SK 00143', 'budget', 353006, 1.1, 77, 8, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FMJK1H58EE534113', 'SK 00144', 'premium', 360684, 9.13, 4, 2, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SALVP2BG0FH607025', 'SK 00145', 'premium', 395554, 9.28, 21, 4, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA65535A863044', 'SK 00146', 'standard', 401904, 11.94, 135, 5, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDF98E35A385217', 'SK 00147', 'budget', 449466, 11.43, 3, 1, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KM8JT3AB7CU966318', 'SK 00148', 'standard', 466071, 1.69, 4, 6, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUAH74F78N764749', 'SK 00149', 'premium', 166531, 14.02, 197, 5, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JH4NA21662T807902', 'SK 00150', 'standard', 497355, 7.81, 142, 6, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTDKTUD38DD347649', 'SK 00151', 'premium', 270245, 5.63, 101, 9, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAYB6C57ED763332', 'SK 00152', 'budget', 176610, 9.71, 43, 2, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1N4AL2AP8CN256645', 'SK 00153', 'standard', 265722, 4.74, 10, 2, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1D7CW7GP9AS527571', 'SK 00154', 'standard', 203190, 4.98, 169, 9, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5TDBKRFH0ES769476', 'SK 00155', 'standard', 216863, 12.87, 117, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5TDDCRFH0FS154031', 'SK 00156', 'standard', 411690, 3.0, 152, 3, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2FMDK3AK5DB096492', 'SK 00157', 'standard', 10710, 1.95, 164, 8, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV4952CF0E1287827', 'SK 00158', 'standard', 485509, 7.04, 192, 2, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3D7TP2CT6AG063889', 'SK 00159', 'budget', 131480, 12.52, 132, 9, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KMHHT6KD2AU323453', 'SK 00160', 'budget', 28751, 9.12, 36, 9, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBADX7C58DE235362', 'SK 00161', 'budget', 422295, 13.39, 83, 9, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN8AZ1MU7CW462672', 'SK 00162', 'budget', 292802, 1.36, 185, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5N1BA0ND1FN188794', 'SK 00163', 'standard', 30363, 12.21, 33, 3, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTEW1CF2FK754151', 'SK 00164', 'budget', 338595, 1.23, 196, 5, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1CN7AP4FL976347', 'SK 00165', 'premium', 306329, 4.39, 82, 5, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6DK8EG0A0210277', 'SK 00166', 'budget', 230347, 2.14, 28, 2, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6AB5RX9D0373347', 'SK 00167', 'budget', 193323, 7.01, 154, 3, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAPK5C53AA580382', 'SK 00168', 'standard', 29250, 3.0, 83, 3, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUKF98E08A592689', 'SK 00169', 'standard', 124279, 5.39, 101, 7, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3V9C51F5161105', 'SK 00170', 'premium', 476888, 5.45, 33, 3, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAULC68E63A552671', 'SK 00171', 'standard', 150407, 14.03, 72, 8, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KMHTC6AD4DU716323', 'SK 00172', 'premium', 84920, 8.09, 69, 9, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WDCYC3HF8AX935080', 'SK 00173', 'budget', 27591, 2.79, 27, 5, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV126MFK9F2455797', 'SK 00174', 'premium', 333196, 1.65, 15, 6, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTSW3B55AE295406', 'SK 00175', 'premium', 373797, 5.04, 76, 7, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBABS33443J841303', 'SK 00176', 'budget', 26620, 7.66, 97, 9, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KL4CJFSB3FB711680', 'SK 00177', 'standard', 74148, 6.04, 61, 5, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKS2GEJ8DR837596', 'SK 00178', 'standard', 408701, 2.43, 98, 8, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1N6AA0CA4FN225777', 'SK 00179', 'budget', 333394, 3.15, 34, 10, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('137FA84361E777638', 'SK 00180', 'premium', 152032, 3.15, 82, 10, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3C1C59EK852230', 'SK 00181', 'premium', 366487, 9.61, 124, 8, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBABW33476P297062', 'SK 00182', 'premium', 420692, 12.1, 107, 3, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUFFBFL8CN558768', 'SK 00183', 'premium', 51671, 11.81, 134, 9, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUKH98E18A852589', 'SK 00184', 'budget', 87246, 6.86, 185, 8, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SCFFBCBD3BG523143', 'SK 00185', 'premium', 205646, 4.55, 196, 10, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3D4PG3FG2BT127728', 'SK 00186', 'premium', 392046, 4.4, 191, 2, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5GADS13S772775720', 'SK 00187', 'standard', 202817, 5.59, 152, 7, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WVWGU7AN8AE457875', 'SK 00188', 'premium', 245638, 10.64, 54, 10, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAULC58E13A433792', 'SK 00189', 'standard', 188615, 13.19, 200, 1, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WVWED7AJ2BW332189', 'SK 00190', 'standard', 352421, 12.62, 63, 5, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV1672MK1C2453916', 'SK 00191', 'standard', 136198, 2.85, 140, 8, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAWL13569P934051', 'SK 00192', 'standard', 198545, 10.67, 152, 7, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFNFE33ES569775', 'SK 00193', 'budget', 239110, 11.07, 71, 9, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JA4AS2AW1BU498163', 'SK 00194', 'budget', 386004, 8.39, 17, 3, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUHF98P38A022990', 'SK 00195', 'premium', 1198, 3.91, 112, 6, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5GAEV23D89J736341', 'SK 00196', 'budget', 176330, 9.47, 128, 5, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAYB6C53FG345754', 'SK 00197', 'standard', 241963, 11.23, 41, 9, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1N4AA5AP4CC066635', 'SK 00198', 'standard', 23040, 6.5, 149, 8, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUTFAFH9DN978520', 'SK 00199', 'premium', 194105, 13.7, 77, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAKG1C53CE973430', 'SK 00200', 'budget', 179490, 2.73, 105, 8, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SCFEBBCFXCG988784', 'SK 00201', 'standard', 44536, 2.16, 64, 4, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1B3CB9HA6BD687118', 'SK 00202', 'premium', 481297, 13.43, 129, 1, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAVB13566P407394', 'SK 00203', 'budget', 100821, 14.11, 101, 10, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SALAG2D45BA501710', 'SK 00204', 'premium', 292753, 1.51, 138, 3, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1N4AA5AP1EC472634', 'SK 00205', 'standard', 392610, 1.99, 66, 9, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FMJK1F58AE723504', 'SK 00206', 'premium', 399804, 11.42, 195, 1, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUEH78E26A882778', 'SK 00207', 'budget', 176863, 3.22, 101, 3, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3C63DPHL8CG574667', 'SK 00208', 'standard', 44516, 10.62, 108, 9, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFK66N45G067157', 'SK 00209', 'standard', 345893, 11.2, 22, 9, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUDH74FX8N608301', 'SK 00210', 'budget', 327881, 7.09, 64, 2, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2HNYD18211H243875', 'SK 00211', 'budget', 135963, 9.48, 177, 10, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3G5DA03L46S670745', 'SK 00212', 'premium', 93871, 6.74, 50, 8, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5N1AA0NC9CN740248', 'SK 00213', 'standard', 167109, 2.14, 113, 6, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUUL98EX7A849728', 'SK 00214', 'budget', 330399, 6.5, 155, 3, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTMF1CW0AF285896', 'SK 00215', 'premium', 63394, 14.02, 164, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G6AB5SX2D0851375', 'SK 00216', 'budget', 101160, 11.39, 199, 8, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUAH78E08A043813', 'SK 00217', 'standard', 46317, 11.57, 181, 4, 8);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FMJU1H5XAE217616', 'SK 00218', 'budget', 99329, 1.44, 182, 9, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G4HA5EM2AU510106', 'SK 00219', 'budget', 272489, 1.1, 173, 9, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1C3CCBBB3EN076180', 'SK 00220', 'budget', 306501, 9.96, 39, 10, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1N6AA0CH1DN709853', 'SK 00221', 'premium', 405500, 8.48, 131, 4, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAULD64B33N767426', 'SK 00222', 'standard', 73670, 14.19, 71, 7, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFNBEY6AS793041', 'SK 00223', 'budget', 416706, 9.34, 94, 5, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UUA66244A851715', 'SK 00224', 'budget', 332528, 9.83, 46, 1, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1B3CC1FB3AN006464', 'SK 00225', 'budget', 201853, 11.26, 157, 6, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4T3BA3BB4DU072150', 'SK 00226', 'premium', 80757, 3.39, 123, 7, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTSW3A51AE311537', 'SK 00227', 'budget', 492329, 10.94, 25, 3, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAU2MAFC0FN066142', 'SK 00228', 'budget', 202203, 9.64, 114, 3, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYUCCEF3AR481098', 'SK 00229', 'premium', 353312, 4.21, 128, 7, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUFFAFL3FN920360', 'SK 00230', 'standard', 75103, 4.92, 165, 10, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3JEF6BR518335', 'SK 00231', 'standard', 154931, 8.83, 195, 10, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAPK5C56BA038307', 'SK 00232', 'standard', 33504, 9.99, 172, 9, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAAZ33473K776903', 'SK 00233', 'standard', 423250, 12.08, 101, 10, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GYS3KEF9BR464793', 'SK 00234', 'premium', 59261, 11.84, 147, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5N1AA0ND6FN415165', 'SK 00235', 'premium', 481019, 9.08, 63, 4, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WP0AB2A90AS986305', 'SK 00236', 'budget', 132118, 13.52, 197, 4, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('SAJWA4FB4DL158886', 'SK 00237', 'budget', 293299, 7.15, 84, 1, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1G4GL5E35DF654030', 'SK 00238', 'budget', 29827, 6.48, 37, 3, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBADN534X2G655305', 'SK 00239', 'standard', 233100, 8.82, 11, 3, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WVGAV7AX8AW628201', 'SK 00240', 'standard', 55769, 9.49, 135, 6, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1NXBE4EE7AZ825531', 'SK 00241', 'standard', 483520, 2.54, 78, 7, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2T1BU4EE2CC378064', 'SK 00242', 'premium', 222483, 8.14, 89, 4, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUPN44E87N375982', 'SK 00243', 'budget', 332874, 9.45, 111, 5, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3GYFNDEY7AS476557', 'SK 00244', 'standard', 71516, 14.91, 166, 1, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3C3CFFAR7ET369884', 'SK 00245', 'budget', 307720, 1.2, 175, 3, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3G5DA03E35S778080', 'SK 00246', 'premium', 166437, 6.32, 71, 6, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN1CV6EK0FM876907', 'SK 00247', 'premium', 349023, 2.32, 43, 2, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WA1BY94L28D406886', 'SK 00248', 'standard', 358567, 6.2, 181, 9, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4T1BF1FK6FU213103', 'SK 00249', 'budget', 3049, 9.28, 119, 4, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTJHY7AX3A4649542', 'SK 00250', 'premium', 490817, 6.46, 55, 6, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUMGAFL8AA099258', 'SK 00251', 'premium', 246546, 9.85, 183, 6, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUAH94F17N591918', 'SK 00252', 'premium', 214748, 2.33, 115, 10, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KNAFK4A65E5046977', 'SK 00253', 'standard', 213600, 8.97, 63, 5, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUVT68E13A530232', 'SK 00254', 'budget', 80011, 6.8, 113, 7, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5TDBK3EH1DS897707', 'SK 00255', 'budget', 178006, 13.61, 30, 7, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('YV4852CZ9B1664074', 'SK 00256', 'premium', 2580, 5.63, 94, 6, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5J8TB4H75GL332283', 'SK 00257', 'premium', 75141, 6.59, 90, 1, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1D7RW2BKXAS549667', 'SK 00258', 'premium', 476195, 10.05, 70, 10, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTMHY7AJ7F4653423', 'SK 00259', 'premium', 245739, 10.02, 115, 4, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1HGCP2E87AA313224', 'SK 00260', 'premium', 19279, 13.37, 78, 5, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN8AE2KP2D9479992', 'SK 00261', 'budget', 498699, 3.31, 129, 9, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5GADV23L65D832934', 'SK 00262', 'premium', 305839, 5.96, 122, 4, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAU4FAFR4AA601030', 'SK 00263', 'budget', 61066, 4.7, 6, 1, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAKE5C51DJ141798', 'SK 00264', 'standard', 269769, 11.05, 113, 7, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WA1DGAFE7CD055641', 'SK 00265', 'standard', 63781, 14.29, 190, 4, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1GKS1GEJ9DR108428', 'SK 00266', 'premium', 180738, 1.49, 94, 2, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTMF1E80AF754631', 'SK 00267', 'budget', 34982, 2.7, 166, 2, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2B3CK3CV5AH195169', 'SK 00268', 'standard', 75876, 9.83, 194, 6, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAUN13599V626627', 'SK 00269', 'premium', 459123, 9.17, 67, 8, 11);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5NPEB4AC2CH583212', 'SK 00270', 'premium', 172732, 2.47, 50, 4, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTDKTUD31DD754311', 'SK 00271', 'budget', 433926, 5.87, 140, 6, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JH4CU25649C475199', 'SK 00272', 'standard', 489607, 9.51, 154, 2, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1FTWW3CY0AE627925', 'SK 00273', 'budget', 122655, 6.12, 168, 4, 9);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WP1AA2A23DL630317', 'SK 00274', 'budget', 396998, 10.67, 174, 7, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WA1WMBFE6DD772752', 'SK 00275', 'standard', 253300, 5.15, 18, 10, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBAEN33403E705344', 'SK 00276', 'premium', 170857, 3.2, 88, 5, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('19UYA42742A365703', 'SK 00277', 'premium', 103329, 4.16, 103, 6, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBA3T3C58EJ956032', 'SK 00278', 'premium', 310592, 1.76, 172, 1, 13);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUHF98P58A977841', 'SK 00279', 'standard', 86402, 9.47, 54, 4, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5UXWX5C59CL112310', 'SK 00280', 'standard', 241974, 7.4, 35, 1, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WBANA535X4B798344', 'SK 00281', 'budget', 6178, 10.9, 66, 5, 5);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2D4RN4DE9AR590702', 'SK 00282', 'premium', 220990, 7.78, 24, 5, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5J8TB3H55FL591332', 'SK 00283', 'budget', 243202, 4.24, 121, 1, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4A31K5DF1AE400225', 'SK 00284', 'standard', 438746, 5.52, 147, 9, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JHMFA3F22BS343030', 'SK 00285', 'budget', 146988, 4.44, 67, 8, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JH4CU4F45BC238896', 'SK 00286', 'premium', 304017, 1.68, 193, 1, 3);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUGL98E46A982609', 'SK 00287', 'standard', 137174, 3.26, 162, 9, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('4JGBF2FB6AA793596', 'SK 00288', 'premium', 412310, 14.22, 91, 2, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JHMZF1D49FS847541', 'SK 00289', 'premium', 175451, 11.72, 83, 9, 12);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('KNDKG3A28A7238046', 'SK 00290', 'standard', 202205, 1.59, 27, 4, 4);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('5TDDCRFH3ES292239', 'SK 00291', 'premium', 45829, 7.9, 167, 4, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JN1CV6EK9BM721671', 'SK 00292', 'premium', 141614, 11.83, 52, 6, 7);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('2FMGK5BC7BB029034', 'SK 00293', 'standard', 483332, 1.28, 193, 8, 15);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUHFBFL7CN291177', 'SK 00294', 'standard', 239461, 3.35, 32, 7, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTJBARBZXF2342992', 'SK 00295', 'premium', 249773, 14.8, 191, 2, 14);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WAUFFAFL9DA204602', 'SK 00296', 'budget', 353756, 2.63, 177, 5, 1);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('1C3CDWBA0CD450517', 'SK 00297', 'budget', 239866, 4.43, 58, 5, 10);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('WA1WMAFP3EA521556', 'SK 00298', 'budget', 334333, 11.54, 36, 2, 6);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('JTDZN3EU5E3631516', 'SK 00299', 'premium', 326255, 13.17, 50, 8, 2);
insert into Vehicles (vin, plates, equipmentLevel, mileage, avgFuelConsumption, Brands_Models_id, Types_id, Purposes_id) values ('3N1CN7AP5EL985783', 'SK 00300', 'budget', 478154, 3.12, 15, 9, 12);


insert into Functions (name) values ('test1');
insert into Functions (name) values ('test2');
insert into Functions (name) values ('test3');
insert into Functions (name) values ('test3');


insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Ingram', 'Jeremiah', '520-913-8681', 'hterris0@pcworld.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Drusilla', 'Bohling', '435-646-1485', 'lposer1@acquirethisname.com','50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Kristi', 'Lutwyche', '330-992-0969', 'kbinnell2@soundcloud.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Barr', 'Bonson', '873-338-5315', 'skegley3@gmpg.org', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Rickie', 'Ilyasov', '646-841-7306', 'lkillelea4@163.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Asher', 'Yakovliv', '602-894-8700', 'uschule5@dion.ne.jp', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Tandy', 'Antonijevic', '139-786-9669', 'ybarmby6@dropbox.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Xever', 'Bottrell', '492-887-8711', 'pfine7@miitbeian.gov.cn', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Derk', 'Mounter', '341-867-9363', 'aedlin8@freewebs.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Tan', 'Dumke', '794-808-8152', 'simloch9@boston.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Yanaton', 'Caiger', '769-148-1131', 'mernia@engadget.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Ola', 'Lye', '638-298-6600', 'lmcguckinb@chronoengine.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Inger', 'Flowerdew', '480-665-1471', 'sivec@nationalgeographic.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Edith', 'Hazlehurst', '327-709-1189', 'fesselend@symantec.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Kaja', 'Moorcraft', '700-895-2478', 'mspringhame@google.co.uk', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Abigael', 'Lockey', '459-322-2852', 'kbowfinf@mit.edu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Joelie', 'Scotts', '870-531-1498', 'mrixong@baidu.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Kipp', 'Cavaney', '368-317-1820', 'esonleyh@slashdot.org', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Wald', 'Bebbington', '424-776-3385', 'tstandagei@harvard.edu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Flo', 'Chaunce', '389-250-7716', 'tspavinsj@boston.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Giulietta', 'Cecchi', '276-392-0869', 'ageraudelk@yelp.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Caprice', 'Impleton', '864-811-7644', 'moffal@ucsd.edu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Kellen', 'Maureen', '703-297-7917', 'udewburym@paypal.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Sammy', 'Francesco', '312-176-4471', 'dtinstonn@exblog.jp', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Zollie', 'Shirrell', '914-349-6375', 'rjanauscheko@fda.gov', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Pattin', 'Paulmann', '753-126-2697', 'squadrip@ca.gov', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Coleen', 'Handmore', '698-441-8417', 'gmchardyq@jigsy.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Fraser', 'Huburn', '621-505-3796', 'hduttr@hibu.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Harmon', 'Beckingham', '932-691-3686', 'szolinis@google.com.br', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Lida', 'Jovovic', '468-619-7751', 'tcoitet@oracle.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Joana', 'Lahrs', '229-907-0488', 'schadbournu@columbia.edu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Halimeda', 'Kirk', '943-509-0136', 'abravev@businessweek.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Nicol', 'Steabler', '419-836-5827', 'gkerfutw@fastcompany.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Andris', 'Yepiskov', '423-296-9854', 'scamberx@de.vu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Maiga', 'Pagelsen', '777-642-6555', 'bbradery@telegraph.co.uk', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Rickey', 'Bembrigg', '704-725-2162', 'galflattz@prnewswire.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Cort', 'Childerhouse', '340-952-5737', 'cjaskowicz10@yale.edu', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Nadia', 'Headan', '588-551-5138', 'kmeert11@fotki.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Gardie', 'Streeting', '287-906-4746', 'ffrawley12@toplist.cz', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Tyson', 'Siss', '826-743-7669', 'anajera13@skype.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Theresa', 'Attwill', '547-786-7704', 'mtutt14@hibu.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Catarina', 'Bresnen', '643-920-2880', 'hshelborne15@tripadvisor.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Griz', 'Praten', '114-761-2191', 'lvenny16@marketwatch.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Lacie', 'Killough', '205-605-7447', 'ebruhnke17@sciencedaily.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Carmine', 'Kirgan', '612-549-0022', 'kjohannesson18@google.co.uk', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Sergei', 'Puttrell', '566-674-1158', 'bmaycey19@google.ru', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Aloysia', 'Ryles', '984-335-2089', 'hely1a@booking.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 3);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Agnes', 'Rohlfing', '138-970-1738', 'owebbe1b@independent.co.uk', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 4);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Everett', 'Queyos', '956-245-9291', 'hbewsey1c@telegraph.co.uk', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 1);
insert into People (firstName, lastName, phoneNumber, mail, password, Functions_id) values ('Dinny', 'Twelve', '180-787-3364', 'ddoers1d@go.com', '50241a2ad08ba0ef53c25951f946f78fce0daf9ba02ee50b7c030efdfad7468e', 2);


insert into Subcontractors (name, address, phoneNumber) values ('Yadel', '712 Hallows Street', '395-166-9967');
insert into Subcontractors (name, address, phoneNumber) values ('Edgetag', '0623 Loeprich Drive', '129-708-3630');
insert into Subcontractors (name, address, phoneNumber) values ('Zoonder', '56 Fallview Parkway', '702-765-8665');
insert into Subcontractors (name, address, phoneNumber) values ('Realcube', '084 Arizona Alley', '569-410-6514');
insert into Subcontractors (name, address, phoneNumber) values ('Tazz', '7 Twin Pines Lane', '229-911-8978');
insert into Subcontractors (name, address, phoneNumber) values ('Fiveclub', '51246 Eagle Crest Court', '308-628-5271');
insert into Subcontractors (name, address, phoneNumber) values ('Yakidoo', '8 Golf View Road', '147-868-7393');
insert into Subcontractors (name, address, phoneNumber) values ('Wikizz', '5 Springview Lane', '626-351-5327');
insert into Subcontractors (name, address, phoneNumber) values ('Voonix', '8 Stephen Terrace', '997-729-4758');
insert into Subcontractors (name, address, phoneNumber) values ('Devcast', '94837 Moland Trail', '795-219-9495');
insert into Subcontractors (name, address, phoneNumber) values ('Dabshots', '1122 Lakewood Gardens Center', '314-209-8483');
insert into Subcontractors (name, address, phoneNumber) values ('Npath', '7 Hovde Trail', '529-829-9294');
insert into Subcontractors (name, address, phoneNumber) values ('Yacero', '1374 Del Mar Trail', '652-604-3063');
insert into Subcontractors (name, address, phoneNumber) values ('Meezzy', '860 Di Loreto Hill', '994-267-2841');
insert into Subcontractors (name, address, phoneNumber) values ('Tagcat', '3 Spaight Circle', '627-204-0315');
insert into Subcontractors (name, address, phoneNumber) values ('Tagtune', '171 Valley Edge Court', '910-159-4341');
insert into Subcontractors (name, address, phoneNumber) values ('Tekfly', '000 Messerschmidt Avenue', '238-225-9353');
insert into Subcontractors (name, address, phoneNumber) values ('Youfeed', '2 Brickson Park Avenue', '639-829-0401');
insert into Subcontractors (name, address, phoneNumber) values ('Omba', '8 Caliangt Avenue', '102-592-2784');
insert into Subcontractors (name, address, phoneNumber) values ('Skinder', '8497 Troy Lane', '510-431-1669');
insert into Subcontractors (name, address, phoneNumber) values ('Dabjam', '05714 Dayton Trail', '381-296-2199');
insert into Subcontractors (name, address, phoneNumber) values ('Kwilith', '9 Towne Crossing', '616-670-0895');
insert into Subcontractors (name, address, phoneNumber) values ('Ooba', '6000 Coleman Street', '595-438-7492');
insert into Subcontractors (name, address, phoneNumber) values ('Oba', '00 Laurel Drive', '602-766-3547');
insert into Subcontractors (name, address, phoneNumber) values ('Skyba', '0 Bashford Point', '350-813-8631');
insert into Subcontractors (name, address, phoneNumber) values ('Roomm', '7564 Loomis Point', '552-250-3539');
insert into Subcontractors (name, address, phoneNumber) values ('Fivechat', '083 Loftsgordon Way', '404-134-0616');
insert into Subcontractors (name, address, phoneNumber) values ('Zava', '7 Bonner Plaza', '738-793-6475');
insert into Subcontractors (name, address, phoneNumber) values ('Demizz', '84 Independence Lane', '205-860-1714');
insert into Subcontractors (name, address, phoneNumber) values ('Zoovu', '4811 Stone Corner Avenue', '713-443-4166');
insert into Subcontractors (name, address, phoneNumber) values ('Twimbo', '333 Melvin Road', '405-575-1240');
insert into Subcontractors (name, address, phoneNumber) values ('Bubbletube', '567 Center Parkway', '154-267-9213');
insert into Subcontractors (name, address, phoneNumber) values ('Twimm', '901 South Place', '850-782-1892');
insert into Subcontractors (name, address, phoneNumber) values ('Dazzlesphere', '3772 Eagle Crest Place', '228-699-8905');
insert into Subcontractors (name, address, phoneNumber) values ('Buzzdog', '12821 Mcguire Lane', '936-307-5286');
insert into Subcontractors (name, address, phoneNumber) values ('Janyx', '91700 Pierstorff Junction', '340-365-0649');
insert into Subcontractors (name, address, phoneNumber) values ('Thoughtmix', '70 Hoard Park', '558-347-3769');
insert into Subcontractors (name, address, phoneNumber) values ('Innotype', '9 Reindahl Street', '562-143-7869');
insert into Subcontractors (name, address, phoneNumber) values ('Fanoodle', '42609 Mayfield Center', '515-658-1286');
insert into Subcontractors (name, address, phoneNumber) values ('Skyndu', '5132 Packers Drive', '883-100-1375');
insert into Subcontractors (name, address, phoneNumber) values ('Vinte', '04903 Birchwood Avenue', '931-230-4139');
insert into Subcontractors (name, address, phoneNumber) values ('Skyble', '75292 Bartillon Hill', '968-899-0094');
insert into Subcontractors (name, address, phoneNumber) values ('Mycat', '82651 Harbort Junction', '636-693-1980');
insert into Subcontractors (name, address, phoneNumber) values ('Jaxbean', '19 Clove Way', '514-589-5187');
insert into Subcontractors (name, address, phoneNumber) values ('Flashpoint', '5 Larry Junction', '794-175-9660');
insert into Subcontractors (name, address, phoneNumber) values ('Kanoodle', '81243 Warner Alley', '973-163-2625');
insert into Subcontractors (name, address, phoneNumber) values ('Flashspan', '40333 Tony Road', '559-165-7247');
insert into Subcontractors (name, address, phoneNumber) values ('Gabspot', '294 Gateway Drive', '517-815-1233');
insert into Subcontractors (name, address, phoneNumber) values ('Tekfly', '0266 Cascade Trail', '385-956-5529');
insert into Subcontractors (name, address, phoneNumber) values ('Photobug', '58 Montana Parkway', '794-148-2781');