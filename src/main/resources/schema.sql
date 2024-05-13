CREATE TABLE IF NOT EXISTS Customer
(
	id TEXT NOT NULL PRIMARY KEY,
  	name TEXT NOT NULL,
  	country TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders
(
	id TEXT NOT NULL PRIMARY KEY,
  	customer_id TEXT NOT NULL,
  	total NUMERIC(10,2) NOT NULL  	
);