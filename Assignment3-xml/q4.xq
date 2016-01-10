for $p in fn:doc("people.xml")//Person[Name/First="James" and Name/Last="Cameron"]/@PID
for $d in fn:doc("movies.xml")//Movie[@year>=2001 and Director/@PID=$p]
return ($d/Title, $d/@year)
