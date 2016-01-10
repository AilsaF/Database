set search_path to artistdb;

create view Musician as 
select name as artist, role as capacity, count(distinct(genre_id)) as genres
from Artist natural join Album natural join Role
where role !='Songwriter'
group by role, name
having count(distinct(genre_id))>2
order by genres desc, name;


create view Songwriter as
select distinct name as artist, 'Songwirter' as capacity, count(distinct(genre_id)) as genres
from Song S, BelongsToAlbum B, Album L, Artist A
where S.song_id=B.song_id and B.album_id=L.album_id and A.artist_id=S.songwriter_id
group by A.name
having count(distinct(genre_id))>2
order by genres desc, name;

(select * from Musician) union all (select * from Songwriter);

drop view Musician, Songwriter;


