# Ruiwa

類は友を呼ぶ

A site that allows users to see a [twitter](https://twitter.com/) user’s
“Twitter Friends”—two persons whose tweets are similar in content in some way.

## Usage

*GOAL*
When a user enters a twitter handle, display a set of this person’s friends,
along with their twitter handles, profile pictures, and the measures of closeness.

At this time, the algorithm focuses on direct interactions. (Later I may try to
have it focus on people with similar interactions, which requires a more
extensive search.)

Running from the command line:
```
$ java -jar target/uberjar/ruiwa-0.1.0-SNAPSHOT-standalone.jar config.edn randommood

{ :score 63
, :description Gopher
, :profile_image_url_https https://pbs.twimg.com/profile_images/59859794/dmg-simpsons-head_normal.png
, :name Damian Gryski, :screen_name dgryski, :url https://t.co/s5Lu4IHGox }
{ :score 62
, :description Door to door leenuux salesperson. https://t.co/0ZsE6Pqh7g
, :profile_image_url_https https://pbs.twimg.com/profile_images/828431511795830786/DRYMDwSi_normal.jpg
, :name jessie frazelle, :screen_name jessfraz, :url https://blog.jessfraz.com }
{ :score 60
, :description Backend Brat & Distributed Systems Diva @twitter Formerly #343i building @Halo Services. Valkyrie AF.
, :profile_image_url_https https://pbs.twimg.com/profile_images/761417208249536512/ddJvQVXd_normal.jpg
, :name Caitie McCaffrey, :screen_name caitie, :url https://t.co/436CWqSZwQ }
{ :score 59
, :description Author: #themorningpaper, Venture Partner with Accel Partners, London after many years as CTO for SpringSource and then apps at VMware, Pivotal
, :profile_image_url_https https://pbs.twimg.com/profile_images/756041698904596480/qm6IkUot_normal.jpg
, :name Adrian Colyer, :screen_name adriancolyer, :url https://t.co/b74UImWlMg }
{ :score 55
, :description programming and exclamation marks.  ♥ @recursecenter, @bangbangcon
, :profile_image_url_https https://pbs.twimg.com/profile_images/827289061324787712/x_bAhuZU_normal.jpg
, :name Julia Evans, :screen_name b0rk, :url http://t.co/9Wxp3hwduY }
```

## License

Copyright © 2017 Michael S. Daines

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
