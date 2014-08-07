affectr-java-2
==============

A simple Java client for [TheySay AffectR API](http://docs.theysay.apiary.io).

# Dependencies

The client has been tested on `Java 1.7.0_60` on `OS X 10.8.2`.

The project is built with `SBT 0.12.2` using the following dependencies:

```
"ch.qos.logback" % "logback-classic" % "1.0.12",
"com.mashape.unirest" % "unirest-java" % "1.3.8",
"net.sf.jopt-simple" % "jopt-simple" % "4.6",
"org.apache.commons" % "commons-lang3" % "3.1"
```

# Running Examples

```
$ sbt
> compile
> run -b http://api.theysay.io/v1 -u yourUserName -p yourPassWord"
> exit
```

`Example.java` submits the sample texts from `Texts.java` to [TheySay AffectR API](http://docs.theysay.apiary.io) asynchronously and displays the results in the console.

# Licence

[Apache 2 Licence](http://www.apache.org/licenses/LICENSE-2.0.html). Copyright 2014 TheySay Ltd.

For further details, see the [LICENCE](LICENCE) file.
