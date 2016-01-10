let $m := doc("movies.xml")
let $o := doc("oscars.xml")

for $movie in $m//Movie
for $oscar in $movie//Oscar/@OID
let $ostype := $o//Oscar[@OID=$oscar]/Type
let $otheros := $o//Oscar[Type=$ostype]/@OID
let $minyear := min($o//Oscar[@OID=$otheros]/@year)
let $theoscar := $o//Oscar[@year=$minyear and Type=$ostype]/@OID
let $mintitle := $m//Movie[Oscar/@OID=$theoscar]/Title
return ($ostype, $minyear, $mintitle)










