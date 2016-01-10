set search_path to artistdb;

update WasInBand set end_year='2014'
where artist_id=(select artist_id from Artist where name='Adam Levine')
and band_id=(select artist_id from Artist where name='Maroon 5');

update WasInBand set end_year='2014'
where artist_id=(select artist_id from Artist where name='Mick Jagger')
and band_id=(select artist_id from Artist where name='Rolling Stones');

insert into WasInBand
select A.artist_id, B.artist_id, '2014', '2015'
from Artist A, Artist B
where A.name='Mick Jagger'and B.name='Maroon 5';
