set search_path to artistdb;

select distinct A.name as artist_name, L.title as album_name
from Artist A, Album L
where A.artist_id=L.artist_id and A.artist_id=all (select S1.songwriter_id
                                                   from BelongsToAlbum B1, Song S1
                                                   where B1.song_id=S1.song_id and B1.album_id=L.album_id)
order by artist_name, album_name;
