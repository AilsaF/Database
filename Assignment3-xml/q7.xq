<Stats>
{
for $c in fn:doc("movies.xml")//Category
let $other := count(doc("movies.xml")//Genre[Category=$c])
return <Bar category="{$c/text()}" count="{$other}"/>
}
</Stats>
