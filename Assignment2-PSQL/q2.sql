create view Co as
select artist1, album_id, sales
from Collaboration natural join Album natural join BelongsToAlbum;

create view HaveCo as
select a.artist_id, l.album_id, l.sales
from Co c, Artist a, Album l
where c.artist1=a.artist_id and l.artist_id=a.artist_id;

create view Noco as
select artist_id, sales
from HaveCo
where (artist_id, sales) not in (select artist1, sales from Co);

create view Compare as
select artist1, avg(Co.sales) as coavg, avg(Noco.sales) as navg
from Co, Noco
where Co.artist1=Noco.artist_id
group by artist1;

select name as artists, coavg as avg_collab_sales
from Compare, Artist
where coavg>navg and artist_id=artist1
order by artists;

drop view Co, HaveCo, Noco, Compare;
