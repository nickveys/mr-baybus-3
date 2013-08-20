SHELL		= cmd.exe # Windows bug workaround
PIC 		= 18F2320
CC			= picc18
CFLAGS		= -$(PIC) -O -Zg9
OUTDIR		= bin
OUTFILE 	= $(OUTDIR)\test.hex
SOURCEDIR 	= src
INCLUDEDIR 	= $(SOURCEDIR)\include

all: test.hex clean

test.hex: test.obj delay.obj glk12232.obj
	$(CC) $(CFLAGS) -O$(OUTFILE) test.obj delay.obj glk12232.obj

test.obj: $(SOURCEDIR)\test.c
	$(CC) $(CFLAGS) -C src\test.c

delay.obj: $(INCLUDEDIR)\delay.c $(INCLUDEDIR)\delay.h
	$(CC) $(CFLAGS) -C $(INCLUDEDIR)\delay.c

glk12232.obj: $(INCLUDEDIR)\glk12232.c $(INCLUDEDIR)\glk12232.h
	$(CC) $(CFLAGS) -C $(INCLUDEDIR)\glk12232.c
	
clean:
	del *.lst *.obj
	del $(SOURCEDIR)\*.dep
	del $(INCLUDEDIR)\*.dep
	del $(OUTDIR)\*.cod $(OUTDIR)\*.cof $(OUTDIR)\*.sym