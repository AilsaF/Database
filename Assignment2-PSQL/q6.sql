SET search_path TO artistdb;

create view CanYear as
select artist_id, min(year) as year
from Album natural join Artist
where nationality='Canada' 
group by artist_id;

create view CanFirst as
select artist_id, year, album_id
from CanYear natural join Album;

create view CanSign as
select artist_id, year, Album.album_id
from CanYear natural join Album, ProducedBy
where Album.album_id=ProducedBy.album_id;

create view Indie as
(select artist_id from CanFirst) except all (select artist_id from CanSign);

create view US as 
(select * from Indie) intersect (select artist_id
                                 from Album natural join ProducedBy natural join RecordLabel
                                 where country='America');


select name as artist_name
from US natural join Artist
order by artist_name;

drop view CanYear, CanFirst, CanSign, Indie, US;
