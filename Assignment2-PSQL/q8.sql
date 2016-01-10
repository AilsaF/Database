set search_path to artistdb;

insert into WasInBand
select W.artist_id, W.band_id, '2014', '2015'
from WasInBand W, Artist A
where W.band_id=A.artist_id and name='AC/DC';
