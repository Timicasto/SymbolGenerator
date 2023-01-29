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

<br>

fuck:
<br>
revert the previous adding change.

<br>

addpins:
<br>
syntax: addpins [I/O/B/P) [int/count) [string/name) [string.../pinid...)
<br>example:<br>
```addpins I 3 VDD A1 B5 D2```
```addpins O 2 LVDS_CLKA 57 58```
```addpins B 10 DDR_VDD C3 C5 C8 D2 J7 K2 A3 B2 E5 F6```

<br>

faddpinto:
<br>
syntax: faddpinto [I/O/B/P) [string/name) [int/start) [int/end) [string.../pinid...)
<br>example:<br>
```faddpinto B DQ 0 7 0 1 2 3 4 5 6 7: generates pin 0 ~ 7 with name DQ0 DQ1 DQ2 DQ3... DQ7```

<br>

diffaddto:
<br>
syntax: diffaddto [I/O/B/P) [string/name) [int/start) [int/end) [string.../pinid...)
<br>example:<br>
```diffaddto B CLK 0 1 0 1 2 3: generates pin 0 1 2 3 with name CLK0P CLK0N CLK1P CLK1N```

<br>

diffadd:
<br>
syntax: diffadd [I/O/B/P) [string/name) [string/pinidP) [string/pinidN)
<br>example:<br>
```diffadd B CLK 0 1: generates pin 0 1 with name CLKP CLKN```

# copy the file content and paste it as a symbol in your KiCAD symbol editor

