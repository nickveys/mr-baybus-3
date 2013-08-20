#ifndef __GLK12232_H
#define __GLK12232_H

#define GLK12232_I2C_ADDR				0x50

#define GLK12232_CMD_BYTE				0xFE
#define GLK12232_AUTO_SCROLL_ON			0x51
#define GLK12232_AUTO_SCROLL_OFF		0x52
#define GLK12232_SET_TEXT_INS_PT		0x47
#define GLK12232_SET_TEXT_INS_PT_TL		0x48
#define GLK12232_SET_TEXT_INS_PT_PXL	0x79
#define GLK12232_SET_FONT				0x31
#define GLK12232_SET_FONT_METRICS		0x32
#define GLK12232_SET_DRAW_COLOR			0x63
#define GLK12232_DRAW_LINE				0x6C
#define GLK12232_CONTINUE_LINE			0x65
#define GLK12232_PUT_PIXEL				0x70
#define GLK12232_DRAW_OUTLINE_RECT		0x72
#define GLK12232_DRAW_SOLID_RECT		0x78
#define GLK12232_INIT_BAR_GRAPH			0x67
#define GLK12232_WRITE_TO_BAR_GRAPH		0x69
#define GLK12232_DISPLAY_SAVED_BITMAP	0x62
#define GLK12232_AUTO_REPEAT_ON			0x7E
#define GLK12232_AUTO_REPEAT_OFF		0x60
#define GLK12232_AUTO_TRANSMIT_KEYS_ON	0x41
#define GLK12232_AUTO_TRANSMIT_KEYS_OFF	0x4F
#define GLK12232_CLEAR_KEY_BUFFER		0x45
#define GLK12232_POLL_KEYPAD			0x26
#define GLK12232_SET_DEBOUNCE_TIME		0x55
#define GLK12232_ERASE_FILE				0xB0
#define GLK12232_PURGE_MEMORY1			0x21
#define GLK12232_PURGE_MEMORY2			0x59
#define GLK12232_PURGE_MEMORY3			0x21
#define GLK12232_UPLOAD_BITMAP			0x5E
#define GLK12232_UPLOAD_FONT			0x24
#define GLK12232_CLEAR_DISPLAY			0x58
#define GLK12232_SET_CONTRAST			0x50
#define GLK12232_SET_CONTRAST_AND_SAVE	0x91
#define GLK12232_BACKLIGHT_ON			0x42
#define GLK12232_BACKLIGHT_OFF			0x46
#define GLK12232_GP_OUTPUT_OFF			0x56
#define GLK12232_GP_OUTPUT_ON			0x57
#define GLK12232_SET_I2C_ADDRESS		0x33
#define GLK12232_READ_MODULE_TYPE		0x37
#define GLK12232_SET_RS232_PORT_SPEED	0x39
#define GLK12232_ENTER_FLOW_CTRL_MODE	0x3A
#define GLK12232_EXIT_FLOW_CTRL_MODE	0x3B
#define GLK12232_SET_SERIAL_NUMBER		0x34
#define GLK12232_READ_SERIAL_NUMBER		0x35
#define GLK12232_READ_VERSION_NUMBER	0x36

// Set text auto scrolling, on = 1, off = 0
void setAutoScroll(unsigned char on);

// Set the insertion point of text @ col,row 
void setTextInsertionPoint(unsigned char col, unsigned char row);

// Set the insertion point of text to the top left
// based on the metrics of the current font
void setTextInsertionPointTopLeft();

// Set the insertion point of text @ x,y
void setTextInsertionPointPixel(unsigned char x, unsigned char y);

// Set the current font
void setFont(unsigned char fontId);

// Set the metrics of a font already in memory
// leftMargin = first pixel column to use for the first character in a row
// topMargin = top pixel row to begin drawing the first row of text
// xSpc = number of pixels between characters
// ySpc = number of pixels between rows of text
// scrollRow = pixel row where scrolling should start
void setFontMetrics(unsigned char leftMargin, unsigned char topMargin,
					unsigned char xSpc, unsigned char ySpc, unsigned char scrollRow);

// Set the color for the various drawing commands
// color = 0 for white, 255 for black, use #define's
#define GLK12232_BLACK 255
#define GLK12232_WHITE 0
void setDrawingColor(unsigned char color);

// Draw a line from x1,y1 to x2,y2
// Uses current drawing color
void drawLine(unsigned char x1, unsigned char y1,
				unsigned char x2, unsigned char y2);

// Continue a line from previous ending point to x,y
// Uses current drawing color
void continueLine(unsigned char x, unsigned char y);

// Set a pixel at x,y
// Uses current drawing color
void putPixel(unsigned char x, unsigned char y);

// Draw a rectangular outline at x1,y1 to x2,y2
// Uses same color defines as setDrawingColor()
void drawOutlineRectangle(unsigned char color, unsigned char x1, unsigned char y1,
							unsigned char x2, unsigned char y2);
							
// Draw a solid rectangle at x1,y1 to x2,y2
// Uses same color defines as setDrawingColor()
void drawSolidRectangle(unsigned char color, unsigned char x1, unsigned char y1,
						unsigned char x2, unsigned char y2);

// Initialize a bar graph at x1,y1 to x2,y2
// num = graph id
// type = Vertical (top or bottom) or Horizontal (left or right), use #define's
#define GLK12232_VERTICAL_BOTTOM	0
#define GLK12232_HORIZONTAL_LEFT	1
#define GLK12232_VERTICAL_TOP		2
#define GLK12232_HORIZONTAL_RIGHT	3
void initBarGraph(unsigned char num, unsigned char type,
					unsigned char x1, unsigned char y1,
					unsigned char x2, unsigned char y2);

// Write to bar graph (num) the value (in pixels)
void writeToBarGraph(unsigned char num, unsigned char value);

// Display the stored image (num) at x,y
void displaySavedBitmap(unsigned char num, unsigned char x, unsigned char y);

// Sets whether the keypad presses repeat automatically
// on = 1, off = 0
// if on, mode = either 200ms Typematic or Key down and Key up, use #define's
// if off, mode is ignored
#define GLK12232_200MS_TYPEMATIC	0
#define GLK12232_KEYDOWN_KEYUP		1
void setAutoRepeat(unsigned char on, unsigned char mode);

// Sets whether keypresses are transmitted to host automatically without polling
// on = 1, off = 0
void setAutoTransmitKeypresses(unsigned char on);

// Clear all unread keypresses
void clearKeyBuffer();

// Poll the keypad for buffered keypresses
// returns 0x00 if no keypresses are buffered
// returns keypress if buffered, MSB = 1 if more are waiting, 0 if not
unsigned char pollKeypad();

// Set the key debounce time
// time = increments of 6.554ms (default = 8)
void setDebounceTime(unsigned char time);

// Erase a file in memory
// type = either font or bitmap, use #define's
// num = reference number
#define GLK12232_FONT	1
#define GLK12232_BITMAP	5
void eraseFile(unsigned char type, unsigned char num);

// Remove all fonts, font metrics, bitmaps and settings from memory
// ~~!DANGEROUS!~~
void purgeMemory();

// UNIMPLEMENTED
void uploadBitmap();

// UNIMPLEMENTED
void uploadFont();

// Clear the display, set cursor at top left
void clearDisplay();

// Set the contrast
// contrast = 0-255, 0 - light, 255 - dark
// save = 1 to save setting, 0 to only set temporarily
void setContrast(unsigned char contrast, unsigned char save);

// Set the backlight
// on = 1, duration = time in minutes (0 = forever)
// if on = 0, off, duration is ignored
void setBacklight(unsigned char on, unsigned char duration);

// Set a general purpose output pin
// num = 1 or 2 for GPO1 or GPO2 respectively
// on = 1, off = 0
void setGeneralPurposeOutput(unsigned char num, unsigned char on);

// Sets write address of the module, must be EVEN
// Read address becomes write address+1
void setI2CAddress(unsigned char address);

// Returns the module type, type list available in documentation
unsigned char readModuleType();

// Set the RS232 port speed
// Choices: 9600, 19.2k, 57.6k, 76.8k, 115k, use #define's
#define GLK12232_9600	0x20
#define GLK12232_19200	0x0F
#define GLK12232_57600	0x95
#define GLK12232_76800	0x03
#define GLK12232_115000	0x8A
void setRS232PortSpeed(unsigned char speed);

// Begins flow control mode
void enterFlowControlMode(unsigned char full, unsigned char empty);

// Ends flow control mode
void exitFlowControlMode();

// Sets the serial number of the module (ONE TIME ONLY, PERMANENT)
void setSerialNumber(unsigned int num);

// Reads the serial number of the module
unsigned int readSerialNumber();

// Reads the firmware version of the module
unsigned char readVersionNumber();

// Print a string
void glk12232Print(unsigned char* text);

#endif
