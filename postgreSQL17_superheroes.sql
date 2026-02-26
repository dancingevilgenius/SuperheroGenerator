-- json_table function
-- is a new feature 
-- you must upgrade to PostgreSQL 17 to take advantage of this new feature.

--json_table 
--          work with JSON data more effectively
--          transforming it into a tabular format 
--          makes it easier to query

-- GitHub: https://github.com/softwareNuggets/PostgreSQL_shorts_resources
--    json_table.sql

-- YT: SoftwareNuggets




-- First, let's create a table to store our JSON data
--drop table dealership_inventory
CREATE TABLE superheroes (
    id SERIAL PRIMARY KEY,
    hero_data JSONB
);




-- Insert a sample JSON object into the table
INSERT INTO superheroes (hero_data) VALUES (
    '{
                "heroName": "Frozen Monk",
                "civilianFirstName": "Hudson",
                "civilianLastName": "Rodriguez",
                "powerSet": [
                        "Time Manipulation",
                        "Poison Generation"
                ],
                "addresses": [
                        {
                                "street": "Glen 2060",
                                "city": "New York",
                                "country": "USA",
                                "postalCode": "07357"
                        },
                        {
                                "street": "Fir 501",
                                "city": "Winston-Salem",
                                "country": "USA",
                                "postalCode": "85383"
                        },
                        {
                                "street": "Birch 6501",
                                "city": "Madison",
                                "country": "USA",
                                "postalCode": "34216"
                        }
                ]
        }'
);


--step 3:  Select all the data
select * from superheroes;








--step 4:  using the json_table function
SELECT 
    jt.*
FROM 
    superheroes,
    json_table(hero_data->'addresses', 
               '$[*]' 
			   COLUMNS 
			   (
                                street 		TEXT 	PATH '$.street',
                                city 	        TEXT	PATH '$.city',
                                postal_code     TEXT    PATH '$.postalCode',
                                country 	TEXT 	PATH '$.country'
                                
               )
    ) AS jt
ORDER BY 
    jt.country DESC;



--step 5: selecting specific columns
SELECT
    addresses.street,
    addresses.city,
    addresses.postal_code,
    addresses.country
FROM 
    superheroes,
    json_table(hero_data->'addresses', 
               '$[*]' COLUMNS (
                   street 	TEXT 		PATH '$.street',
                   city 	TEXT 		PATH '$.city',
                   postal_code  TEXT 		PATH '$.postalCode',
                   country 	TEXT 	        PATH '$.country'
               )
    ) AS addresses
ORDER BY 
    addresses.country DESC;

	


	




-- Now, let's use json_table to query this data
SELECT 
    jt.*,
    f.feature
FROM 
    dealership_inventory,
    json_table(inventory_data->'cars', 
               '$[*]' COLUMNS (
                   make TEXT PATH '$.make',
                   model TEXT PATH '$.model',
                   year INT PATH '$.year',
                   price DECIMAL PATH '$.price',
                   features JSON PATH '$.features'
               )
    ) AS jt
CROSS JOIN LATERAL json_array_elements_text(jt.features) AS f(feature)
WHERE 
    jt.price > 1000 AND f.feature LIKE '%Play';