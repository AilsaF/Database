select label_name as record_label, year, sum(sales) as total_sales
from Album a, RecordLabel r, ProducedBy p
where r.label_id=p.label_id and a.album_id=p.album_id
group by label_name, year
order by record_label , year;

