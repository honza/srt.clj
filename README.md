srt.clj
=======

A parsatron-based parser for SRT subtitles.

*A learning exercise*

Usage
-----

    $ lein run

    ({:content Senator, we're making
                our final approach into Coruscant.,
     :start {:hours 0,
             :minutes 2,
             :seconds 17,
             :ms 440},
     :end {:hours 0,
           :minutes 2,
           :seconds 20,
           :ms 375}}
          
    {:content Very good, Lieutenant.,
     :start {:hours 0,
             :minutes 2,
             :seconds 20,
             :ms 476},
     :end {:hours 0,
           :minutes 2,
           :seconds 22,
           :ms 501}})

License
-------

BSD, short and sweet
