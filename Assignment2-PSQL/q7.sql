set search_path to artistdb;

create view SA as
select Song.song_id, Album.album_id, year, Artist.name, Song.title
from Song natural join BelongsToAlbum, Album natural join Artist
where BelongsToAlbum.album_id=Album.album_id;

select S1.title as song_name, S1.year, S1.name as artist_name
from SA S1, SA S2
where S1.song_id=S2.song_id and S1.album_id<>S2.album_id
order by song_name, year, artist_name;

drop view SA;

