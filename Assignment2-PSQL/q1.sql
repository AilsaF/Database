SET search_path TO artistdb;
SELECT distinct name, nationality
FROM Artist natural join Role
WHERE extract(year from birthdate)=(SELECT MIN(year)
                                    FROM Artist NATURAL JOIN Album
                                    WHERE Artist.name='Steppenwolf') and role='Musician'
ORDER BY name;
