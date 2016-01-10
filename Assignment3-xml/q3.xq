for $p in fn:doc("people.xml")//Person[not(@pob)]
for $d in fn:doc("movies.xml")//Director/@PID
return ($p[@PID=$d]/@PID, $p[@PID=$d]//Last)



