# SymbolGenerator

A CLI app for generating KiCAD Symbols by pins

# Compiling Project

You can compile this with JavaC or IntelliJ Building System, since I didn't upload the environment file, you need to configure the IntelliJ Building System by your self, but you can get a Jar file directly if you use the artifact.

# Using the CLI

Now the help command is in WIP state, so just follow the instructions here.

## commands:

newfile:
<br>
syntax: newfile [string/filename):change the current working file name.
<br>
example:<br>

```$_ newfile TUSB564
Changing current file to TUSB564
TUSB564 $_
```

<br>

createmeta:
<br>
syntax: createmeta [rvfd) [string/str) * 1~4
<br>
example:<br>
```createmeta rvfd U TUSB564 footprint datasheet```
```createmeta vr TUSB564 U```

<br>

addpin:
<br>
syntax: addpin [I/O/B/P) [string/name) [string/pinid)
<br>
example:<br>
```addpin I VDD A1```
```addpin O LVDS_CLKA 57```
```addpin B DDR_D2 C3```

<br>

write:
<br>
syntax: write
<br>
writes the current working file to current working directory

<br>

copy:
<br>
syntax: copy
<br>
write command but the output location is your clipboard

<br>

exit: exit.