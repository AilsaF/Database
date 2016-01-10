for $ac in fn:doc("movies.xml")//Actor/@PID
for $di in fn:doc("movies.xml")//Director[@PID=$ac]/@PID
let $name := doc("people.xml")//Person[@PID=$di]//Last
return ($di, $name)
