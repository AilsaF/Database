for $m in fn:doc("movies.xml")//Movie
return ($m/@MID, count($m//Actor))
