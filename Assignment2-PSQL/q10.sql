set search_path to artistdb;

create view Tsong as
select S.song_id
from Song S, BelongsToAlbum B, Album A
where S.song_id=B.song_id and B.album_id=A.album_id and A.title='Thriller';


delete from Collaboration where song_id in
(select * from Tsong);

update Song
set title='deleteeeeeeunique'
where song_id in (select * from Tsong);

delete from BelongsToAlbum where album_id in
(select album_id from Album where title='Thriller');

delete from ProducedBy where album_id in
(select album_id from Album where title='Thriller');

delete from Album where title='Thriller';

delete from Song where title='deleteeeeeeunique';

drop view Tsong;
