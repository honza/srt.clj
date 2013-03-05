srt.clj
=======

A parsatron-based parser for SRT subtitles.  SRT goes in, JSON comes out.

*A learning exercise*

Usage
-----

    $ cat test-subs.srt
    1
    00:00:01,000 --> 00:00:04,074
    Hello world

    2
    00:00:23,690 --> 00:00:27,570
    What time is it?

    $ lein run test-subs.srt
    [ {
    "content" : "Hello world",
    "start" : {
        "hours" : 0,
        "minutes" : 0,
        "seconds" : 1,
        "ms" : 0
    },
    "end" : {
        "hours" : 0,
        "minutes" : 0,
        "seconds" : 4,
        "ms" : 74
    }
    }, {
    "content" : "What time is it?",
    "start" : {
        "hours" : 0,
        "minutes" : 0,
        "seconds" : 23,
        "ms" : 690
    },
    "end" : {
        "hours" : 0,
        "minutes" : 0,
        "seconds" : 27,
        "ms" : 570
    }
    } ]

License
-------

BSD, short and sweet
