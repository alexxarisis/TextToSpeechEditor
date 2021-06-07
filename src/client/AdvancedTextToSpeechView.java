package client;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

import commands.CommandsFactory;
import commands.ReplayManager;
import model.Document;

public class AdvancedTextToSpeechView implements ActionListener, ComponentListener{

	// references & objects
	private AdvancedTextToSpeechView view;
	private CommandsFactory commandsFactory;
	private Document document;
	private ReplayManager replayManager;
	
	// frame
	private JFrame frame;
	
	// FILE
	private HashMap<String, JMenuItem> fileMenu;
	// EDIT
	private HashMap<String, JMenuItem> editMenu;
	// VIEW
	private JCheckBoxMenuItem blackTheme;
	private HashMap<String, JSlider> viewMenuAudioSettings;
	private HashMap<String, JMenuItem> viewMenuFonts;
	private JButton increaseFontSize, decreaseFontSize;
	// TOOLBAR
	private HashMap<String, AbstractButton> toolbarButtons;
	// DEBUGGER
	private JTextArea debugger;
	
	// for color theme changing
	private Color textColor;
	private Color textBackgroundColor;
	private Color scrollPaneBorderColor;
	// for the font changes
	private Font font;
	private JLabel fontSizeLabel;
	
	// TEXT AREA
	private JPanel textAreaPanel;
	//for changes in the text area
	private JTextArea textArea;
	private JTextArea lines;
	private JScrollPane scrollArea;
	
	public AdvancedTextToSpeechView()
	{
		initializeVariables();
		setupFrame();
	}
	
	/** 
	 * Returns this AdvancedTextToSpeechView object that has all the
	 * frame areas, menus, buttons, managers and the current document.
	 * 
	 * @return AdvancedTextToSpeechView This AdvancedTextToSpeechView object.
	 */
	public AdvancedTextToSpeechView getView()
	{
		return view;
	}

	/**
	 * Returns the main JFrame object of the program containing everything
	 * that the application has displayed.
	 * 
	 * @return JFrame object
	 */
	public JFrame getJFrame() {
		return frame;
	}
	
	/**
	 * Returns the current document of the class.
	 * 
	 * @return Document The current document assigned.
	 */
	public Document getDocument() {
		return document;
	}
	
	/**
	 * Returns the current replay manager.
	 * 
	 * @return ReplayManager The replay manager of this AdvancedTextToSpeechView
	 */
	public ReplayManager getReplayManager() {
		return replayManager;
	}
	
	/**
	 * Returns the JTextArea object representing the document's content and
	 * the user's most recent selected text, if any.
	 * 
	 * @return JTextArea object of the frame
	 */
	public JTextArea getTextArea() {
		return textArea;
	}
	
	/**
	 * Returns the JTextArea object representing the document's line numeration.
	 * 
	 * @return JTextArea object of the frame numbering the lines.
	 */
	public JTextArea getLines() {
		return lines;
	}
	
	/**
	 * Returns any button of the ToolBar area.
	 * 
	 * @param button String specifying which button to return.
	 * @return AbstractButton The button specified, with an abstraction.
	 */
	public AbstractButton getToolbarButton(String button) {
		return toolbarButtons.get(button);
	}
	
	/**
	 * Returns the button associated with swapping color themes
	 * on the view menu.
	 * 
	 * @return JCheckBoxMenuItem
	 */
	public JCheckBoxMenuItem getBlackThemeButton() {
		return blackTheme;
	}
	
	/**
	 * Returns the specified audio settings button/slider.
	 * 
	 * @param name The name of the object we are looking for.
	 * @return that specific JSlider object.
	 */
	public JSlider getAudioSettings(String name) {
		return viewMenuAudioSettings.get(name);
	}
	
	/**
	 * Returns the specified font button on the view menu.
	 * 
	 * @param name The name of the object we are looking for.
	 * @return that specific JMenuItem object.
	 */
	public JMenuItem getViewMenuFontButton(String name) {
		return viewMenuFonts.get(name);
	}
	
	/**
	 * Returns the button used to increase the font size.
	 * 
	 * @return a JButton object used to increase font size.
	 */
	public JButton getIncreaseFontSizeButton() {
		return increaseFontSize;
	}
	
	/**
	 * Returns the current font.
	 * 
	 * @return Font object.
	 */
	public Font getFont() {
		return font;
	}
	
	/**
	 * Sets the text on the label stating the font's size 
	 * to a new value.
	 */
	public void setFontSizeLabel(String newSize) {
		fontSizeLabel.setText(newSize);
	}
	
	/**
	 * Sets the current font to the one given.
	 * 
	 * @param newFont Font object to set the font into.
	 */
	public void setFont(Font newFont) {
		this.font = newFont;
	}
	
	/**
	 * Changes the color values.
	 * 
	 * @param textColor The Color of the text.
	 * @param textBackgroundColor The background Color of the text.
	 * @param scrollPaneBorderColor The Color of the border of the JScrollArea
	 */
	public void setColors(Color textColor, Color textBackgroundColor, Color scrollPaneBorderColor ) {
		this.textColor = textColor;
		this.textBackgroundColor = textBackgroundColor;
		this.scrollPaneBorderColor = scrollPaneBorderColor;
	}
	
	/**
	 * Sets the text of the JTextArea. Also sets if the text will change line
	 * automatically to prevent it from going over the border horizontally
	 * (linewrap capability). Currently only excel files do not need linewrap.
	 * 
	 * @param contents String the text we want to set.
	 * @param linewrap Boolean, true if we want it to have linewrap.
	 */
	public void setTextAndLineWrap(String contents, boolean linewrap)
	{
		// has linewrap depending on the boolean given
		textArea.setText(contents);
		textArea.setLineWrap(linewrap);
		textArea.setWrapStyleWord(linewrap);
		textArea.setCaretPosition(0);
		// setup font
		applyFont();
		
		textAreaPanel.add(scrollArea);
	}
	

	/**
	 * Applies the current font.
	 */
	public void applyFont() {
		scrollArea.getViewport().getView().setFont(font);
		lines.setFont(font);
	}
	
	/**
	 * Changes the color on the text and scroll areas.
	 */
	public void changeTextColorTheme()
	{
		scrollArea.getViewport().getView().setBackground(textBackgroundColor);
		scrollArea.getViewport().getView().setForeground(textColor);
		scrollArea.setBackground(scrollPaneBorderColor);
		textArea.setDisabledTextColor(textColor);
		lines.setForeground(textColor);
		lines.setBackground(textBackgroundColor);
		lines.setFont(font);
		lines.setDisabledTextColor(textColor);
	}
	
	/**
	 * Sets the text on the bottom JTextArea, known as the debugger.
	 * Does not append, just shows the latest update.
	 * 
	 * @param str The string that will be displayed.
	 */
	
	public void debug(String str) {
		debugger.setText(str);
	}
	
	/**
	 * Enables all the buttons associated with open file commands.
	 * If one of them is already enabled, then all the other also are, so it 
	 * skips the enabling.
	 * 
	 * Used to enable the buttons after opening a file.
	 */
	public void enableButtons() {
		if (!fileMenu.get("close").isEnabled()) {
			fileMenu.get("close").setEnabled(true);
			fileMenu.get("save").setEnabled(true);
			editMenu.get("edit").setEnabled(true);
			editMenu.get("stopEditing").setEnabled(true);
			toolbarButtons.get("save").setEnabled(true);
			toolbarButtons.get("close").setEnabled(true);
			toolbarButtons.get("edit").setEnabled(true);
			toolbarButtons.get("play").setEnabled(true);
		}
	}
	
	/**
	 * Disables all of the buttons associated with open file commands.
	 * 
	 * Used to disable everything after closing the file.
	 */
	public void disableButtons() {
		fileMenu.get("close").setEnabled(false);
		fileMenu.get("save").setEnabled(false);
		editMenu.get("edit").setEnabled(false);
		editMenu.get("stopEditing").setEnabled(false);
		toolbarButtons.get("save").setEnabled(false);
		toolbarButtons.get("close").setEnabled(false);
		toolbarButtons.get("edit").setSelected(false);
		toolbarButtons.get("edit").setEnabled(false);
		toolbarButtons.get("useSelectedText").setSelected(false);
		toolbarButtons.get("useSelectedText").setEnabled(false);
		toolbarButtons.get("play").setEnabled(false);
	}
	
	/**
	 * Enabled/Disabled or changes selection accordingly for every button associated
	 * with editing the file, depending on the value given. 
	 * 
	 * @param isEnabled Boolean changing the properties of "Edit Text" & "Stop Editing"
	 * and the ToolBar's  "Use selected text" button.
	 */
	public void enableEditButtons(boolean isEnabled) {
		editMenu.get("edit").setEnabled(!isEnabled);
		editMenu.get("stopEditing").setEnabled(isEnabled);
		toolbarButtons.get("edit").setSelected(isEnabled);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		
		if (str.equals("Exit"))
			System.exit(0);
	}
	
	/**
	 * Updates the line numbering each time the component is resized.
	 */
	@Override
	public void componentResized(ComponentEvent e) {
		if (view.getDocument() != null)
			updateLines();
		
	}

	/** 
	 * Updates the line numbering each time the component is visible again.
	 */
	@Override
	public void componentShown(ComponentEvent e) {
		updateLines();
	}

	
	// NOT USED
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	// NOT USED
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Initializes every variable able to be initialized at the start.
	 * For better clarity.
	 */
	private void initializeVariables() {
		view = this;
		commandsFactory = new CommandsFactory(this);
		document = new Document();
		replayManager = new ReplayManager();
		
		fileMenu = new HashMap<>();
		editMenu = new HashMap<>();
		viewMenuAudioSettings = new HashMap<>();
		viewMenuFonts  = new HashMap<>();
		toolbarButtons = new HashMap<>();
		
		textColor = new Color(245, 245, 250);
		textBackgroundColor = new Color(39, 39, 39);
		scrollPaneBorderColor = Color.DARK_GRAY;
		font = new Font("Helvetica", Font.PLAIN, 12);
	}
	
	/**
	 * Constructs the whole frame, complete with all the buttons etc,
	 * by calling all the other setup methods.
	 */
	private void setupFrame() {
		// Make frame and settings
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(600, 600);
		frame.setTitle("Texnologia Logismikou ALEXANDROS CHARISIS 3361");
				
		// Make all these areas
		frame.setJMenuBar(setupJMenuBarArea());
		frame.add(setupToolbarArea(), BorderLayout.NORTH);
		frame.add(setupTextArea());
		frame.add(setupDebugger(), BorderLayout.SOUTH);
		addListeners();
	}
	
	/**
	 * Constructs the whole JMenuBar, with all the buttons and menu items etc.
	 * 
	 * @return JMenuBar A complete bar containing all the menu objects like
	 * File, Edit, etc.
	 */
	private JMenuBar setupJMenuBarArea()
	{
		// Make menu bar ( File, Edit, View, Help, Window, Settings, etc...)
		JMenuBar menu = new JMenuBar();
		menu.setBackground(Color.DARK_GRAY);	
		menu.setForeground(Color.WHITE);
		
		// Adding the menu items
		menu.add(setupFile());
		menu.add(setupEdit());
		menu.add(setupView());
		
		return menu;
	}
	
	
	/** 
	 * Constructs all the buttons in the ToolBar area.
	 * 
	 * @return JToolBar A complete ToolBar containing all the necessary buttons
	 * and elements.
	 */
	private JToolBar setupToolbarArea()
	{
		JToolBar toolbar = new JToolBar();
		toolbar.setFloatable(false);
		toolbar.setBackground(Color.DARK_GRAY);
		// line separating JMenuBar and JToolBar
		toolbar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
		
		// PhotoShop 19x19 pixel image size
		JButton openFile 				= new JButton(new ImageIcon("Images//open-folder.png"));
		JButton saveFile 				= new JButton(new ImageIcon("Resources//Images//floppy-disk.png"));
		JButton closeFile 				= new JButton(new ImageIcon("Resources//Images//close-doc.png"));
		JToggleButton editFileToolbar 	= new JToggleButton(new ImageIcon("Resources//Images//edit-file.png"));
		JToggleButton selectTextToolbar = new JToggleButton(new ImageIcon("Resources//Images//select-no.png"));
		JButton play 					= new JButton(new ImageIcon("Resources//Images//play-button.png"));
		JToggleButton record 			= new JToggleButton(new ImageIcon("Resources//Images//record-button.png"));
		JButton replay 					= new JButton(new ImageIcon("Resources//Images//replay.png"));
		
		// selected/not selected icons for the JToggleButtons in the ToolBar
		editFileToolbar.setSelectedIcon(new ImageIcon("Resources//Images//dont-edit-file.png"));
		editFileToolbar.setDisabledSelectedIcon(new ImageIcon("Resources//Images//edit-file.png"));
		selectTextToolbar.setSelectedIcon(new ImageIcon("Resources//Images//select-yes.png"));
		selectTextToolbar.setDisabledSelectedIcon(new ImageIcon("Resources//Images//select-no.png"));
		
		// Initialize as false, since there are no files at the start
		saveFile.setEnabled(false);
		closeFile.setEnabled(false);
		editFileToolbar.setEnabled(false);
		selectTextToolbar.setEnabled(false);
		play.setEnabled(false);
		//stop.setEnabled(false);
		
		// customize keys
		openFile.setBackground(Color.DARK_GRAY);
		saveFile.setBackground(Color.DARK_GRAY);
		closeFile.setBackground(Color.DARK_GRAY);
		editFileToolbar.setBackground(Color.DARK_GRAY);
		selectTextToolbar.setBackground(Color.DARK_GRAY);
		play.setBackground(Color.DARK_GRAY);
		record.setBackground(Color.DARK_GRAY);
		replay.setBackground(Color.DARK_GRAY);
		
		openFile.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		saveFile.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		closeFile.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		editFileToolbar.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		selectTextToolbar.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		play.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		record.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		replay.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
		
		toolbarButtons.put("open", openFile);
		toolbarButtons.put("save", saveFile);
		toolbarButtons.put("close", closeFile);
		toolbarButtons.put("edit", editFileToolbar);
		toolbarButtons.put("useSelectedText", selectTextToolbar);
		toolbarButtons.put("play", play);
		toolbarButtons.put("record", record);
		toolbarButtons.put("replay", replay);
		
		// add all and leave
		toolbar.add(openFile);
		toolbar.add(saveFile);
		toolbar.add(closeFile);
		toolbar.add(createSeparatorToolbar());
		toolbar.add(editFileToolbar);
		toolbar.add(selectTextToolbar);
		toolbar.add(createSeparatorToolbar());
		toolbar.add(play);
		toolbar.add(createSeparatorToolbar());
		toolbar.add(record);
		toolbar.add(replay);
		
		return toolbar;
	}
	
	/**
	 * Creates a JButton with a pre-select image-icon to be used as the default
	 * separator for the toolbar
	 * 
	 * @return a JButton object representing a default separator.
	 */
	private JButton createSeparatorToolbar() {
		JButton separator = new JButton(new ImageIcon("Resources//Images//separator.png"));
		separator.setBackground(Color.DARK_GRAY);
		separator.setBorder(BorderFactory.createEmptyBorder());
		separator.setMargin(new Insets(0, 0, 0, 0));
		separator.setEnabled(false);
		return separator;
	}
	
	/**
	 * Constructs the text area. JTextArea and JScrollPane of it,
	 * along with customizing everything to black theme.
	 * ScrollBars too.
	 * 
	 * @return JPanel An area without any text set, ready to be filled.
	 */
	private JPanel setupTextArea()
	{
		textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BorderLayout());
		textAreaPanel.setBackground(Color.GRAY);
		textAreaPanel.setBorder(BorderFactory.createEmptyBorder());
		
		textArea = new JTextArea();
		textArea.setAutoscrolls(true);
		textArea.setMargin(new Insets(30, 20, 20, 20));
		textArea.setEditable(false);
		
		scrollArea =  new JScrollPane(textArea);
		scrollArea.setBorder(BorderFactory.createSoftBevelBorder(
				BevelBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY));
		scrollArea.setHorizontalScrollBarPolicy(
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		// customize scroll bars
		scrollArea.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			
			@Override 
	        protected void configureScrollBarColors(){
				this.thumbDarkShadowColor = Color.GRAY;
	            this.thumbColor = new Color(55, 55, 55);
	            this.thumbHighlightColor = Color.GRAY;
	            this.thumbLightShadowColor = Color.GRAY;
	            this.trackColor = new Color(39, 39, 39);
	            this.trackHighlightColor = new Color(39, 39, 39);
	        }
			
			@Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }
			
	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }
		    
		    private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
		});
		scrollArea.getHorizontalScrollBar().setUI(new BasicScrollBarUI() {
					
			@Override 
	        protected void configureScrollBarColors(){
				this.thumbDarkShadowColor = Color.GRAY;
	            this.thumbColor = new Color(55, 55, 55);
	            this.thumbHighlightColor = Color.GRAY;
	            this.thumbLightShadowColor = Color.GRAY;
	            this.trackColor = new Color(39, 39, 39);
	            this.trackHighlightColor = new Color(39, 39, 39);
	        }
					
			@Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }
	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }
			    
		    private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
		});
		
		lines = new JTextArea();
		lines.setMargin(new Insets(30, 0, 0, 0));
		lines.setAutoscrolls(true);   
		lines.setEnabled(false);
		scrollArea.setRowHeaderView(lines);
		
		// make it black
		changeTextColorTheme();
		
		textAreaPanel.add(scrollArea);
		return textAreaPanel;
	}
	
	/**
	 * Constructs a new JPanel & JTextArea on the bottom of the frame called "debugger".
	 * It is used to print system messages to the user.
	 * 
	 * @return JPanel with the JTextArea assigned to it.
	 */
	private JPanel setupDebugger() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		panel.setForeground(Color.WHITE);
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createBevelBorder(
				BevelBorder.LOWERED, Color.DARK_GRAY, Color.DARK_GRAY));
		
		debugger = new JTextArea();
		debugger.setBackground(textBackgroundColor);
		debugger.setForeground(textColor);
		debugger.setAutoscrolls(true);
		debugger.setMargin(new Insets(-1, 4, 0, 0));
		panel.add(debugger);
		
		return panel;
	}
	
	/**
	 * Generates new JMenuItem buttons for the Files button on the menu bar.
	 * Also updates the fileMenu HashMap with all the buttons.
	 * 
	 * @return JMenu A complete JMenu with all its JMenuItem s working.
	 */
	private JMenu setupFile()
	{
		JMenu file = new JMenu("File");
		file.setOpaque(true);
		file.setBackground(Color.DARK_GRAY);
		file.setForeground(Color.LIGHT_GRAY);
		
		JMenuItem open  = new JMenuItem("Open File...");
		JMenuItem close = new JMenuItem("Close");
		JMenuItem save  = new JMenuItem("Save");
		JMenuItem exit  = new JMenuItem("Exit");
		
		// Initialize as false, since there are no files open in the start
		close.setEnabled(false);
		save.setEnabled(false);
		
		fileMenu.put("open", open);
		fileMenu.put("close", close);
		fileMenu.put("save", save);
		fileMenu.put("exit", exit);
			
		file.add(open);
		file.add(close);
		file.addSeparator();
		file.add(save);
		file.addSeparator();
		file.add(exit);
		
		return file;
	}

	/**
	 * Generates new JMenuItem buttons for the Edit button on the menu bar.
	 * Also updates the editMenu HashMap with all the buttons.
	 * 
	 * @return JMenu A complete JMenu with all its JMenuItem s working.
	 */
	private JMenu setupEdit()
	{
		JMenu edit = new JMenu("Edit");
		edit.setOpaque(true);
		edit.setBackground(Color.DARK_GRAY);
		edit.setForeground(Color.LIGHT_GRAY);
		
		JMenuItem editFile 		  = new JMenuItem("Edit Text");
		JMenuItem stopEditingFile = new JMenuItem("Stop Editing");
		
		// we do not have a file so editFile = false
		// we enable dontEditFile when editFile is pressed
		editFile.setEnabled(false);
		stopEditingFile.setEnabled(false);
		
		editMenu.put("edit", editFile);
		editMenu.put("stopEditing", stopEditingFile);
		
		edit.add(editFile);
		edit.add(stopEditingFile);
		
		return edit;
	}
	
	/**
	 * Generates new buttons for the View button on the menu bar.
	 * Creates a JCheckBoxMenuItem for changing the theme on black or white when pressed.
	 * Also creates buttons for changing font and font size.
	 * 
	 * @return JMenu A complete JMenu with all its buttons working.
	 */
	private JMenu setupView()
	{
		JMenu view = new JMenu("View");
		view.setOpaque(true);
		view.setBackground(Color.DARK_GRAY);
		view.setForeground(Color.LIGHT_GRAY);
		
		//////////////// change color 
		blackTheme = new JCheckBoxMenuItem("Black Theme");
		blackTheme.setSelected(true);
		
		//////////////// audio settings
		JMenu audioSettings = new JMenu("Audio Settings");
		
		JMenu volume = new JMenu("Volume");
		JMenu pitch  = new JMenu("Pitch");
		JMenu rate   = new JMenu("Rate");

		audioSettings.add(volume);
		audioSettings.add(pitch);
		audioSettings.add(rate);
		
		JSlider volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
		JSlider pitchSlider  = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
		JSlider rateSlider   = new JSlider(JSlider.HORIZONTAL, 0, 450, 150);
		
		viewMenuAudioSettings.put("volume", volumeSlider);
		viewMenuAudioSettings.put("pitch", pitchSlider);
		viewMenuAudioSettings.put("rate", rateSlider);
		
		volume.add(volumeSlider);
		pitch.add(pitchSlider);
		rate.add(rateSlider);
		
		//////////////// text options
		JMenu options = new JMenu("Font Options");
		
		// change font
		JMenu changeFont = new JMenu("Font family");
		// available fonts
		JMenuItem helvetica 	  = new JMenuItem("Helvetica");
		JMenuItem timesNewRoman   = new JMenuItem("Times New Roman");
		JMenuItem arial 		  = new JMenuItem("Arial");
		JMenuItem courierNew 	  = new JMenuItem("Courier New");
		JMenuItem bookmanOldStyle = new JMenuItem("Bookman Old Style");
		
		viewMenuFonts.put("helvetica", helvetica);
		viewMenuFonts.put("timesNewRoman", timesNewRoman);
		viewMenuFonts.put("arial", arial);
		viewMenuFonts.put("courierNew", courierNew);
		viewMenuFonts.put("bookmanOldStyle", bookmanOldStyle);
		
		changeFont.add(helvetica);
		changeFont.add(timesNewRoman);
		changeFont.add(arial);
		changeFont.add(courierNew);
		changeFont.add(bookmanOldStyle);
		
		// change font size
		JMenu changeFontSize = new JMenu("Font size");
		
		increaseFontSize = new JButton("Increase  ");
		fontSizeLabel 	 = new JLabel("            " + Integer.toString(font.getSize()) + " ");
		decreaseFontSize = new JButton("Decrease");

		changeFontSize.add(increaseFontSize);
		changeFontSize.add(fontSizeLabel);
		changeFontSize.add(decreaseFontSize);
		
		// put all in options
		options.add(changeFont);
		options.add(changeFontSize);
		
		// and all in view
		view.add(blackTheme);
		view.add(audioSettings);
		view.add(options);
		
		return view;
	}
	
	/**
	 * Assigns listeners (both Action & Caret) to each button.
	 */
	private void addListeners() {
		// MENUS
		
		// file
		fileMenu.get("open").addActionListener(commandsFactory.createCommand("open"));
		fileMenu.get("close").addActionListener(commandsFactory.createCommand("close"));
		fileMenu.get("save").addActionListener(commandsFactory.createCommand("save"));
		
		// edit
		editMenu.get("edit").addActionListener(commandsFactory.createCommand("edit"));
		editMenu.get("stopEditing").addActionListener(commandsFactory.createCommand("edit"));

		// view
		blackTheme.addActionListener(commandsFactory.createCommand("colorTheme"));
		//audio settings
		viewMenuAudioSettings.get("volume").addChangeListener(commandsFactory.createChangeListenerCommand("voice"));
		viewMenuAudioSettings.get("pitch").addChangeListener(commandsFactory.createChangeListenerCommand("voice"));
		viewMenuAudioSettings.get("rate").addChangeListener(commandsFactory.createChangeListenerCommand("voice"));
		//fonts
		viewMenuFonts.get("helvetica").addActionListener(commandsFactory.createCommand("font"));
		viewMenuFonts.get("timesNewRoman").addActionListener(commandsFactory.createCommand("font"));
		viewMenuFonts.get("arial").addActionListener(commandsFactory.createCommand("font"));
		viewMenuFonts.get("courierNew").addActionListener(commandsFactory.createCommand("font"));
		viewMenuFonts.get("bookmanOldStyle").addActionListener(commandsFactory.createCommand("font"));
		increaseFontSize.addActionListener(commandsFactory.createCommand("fontSize"));
		decreaseFontSize.addActionListener(commandsFactory.createCommand("fontSize"));
		
		// TOOLBAR
		toolbarButtons.get("open").addActionListener(commandsFactory.createCommand("open"));
		toolbarButtons.get("save").addActionListener(commandsFactory.createCommand("save"));
		toolbarButtons.get("close").addActionListener(commandsFactory.createCommand("close"));
		toolbarButtons.get("edit").addActionListener(commandsFactory.createCommand("edit"));
		toolbarButtons.get("useSelectedText").addActionListener(commandsFactory.createCommand("updateSelection"));
		toolbarButtons.get("play").addActionListener(commandsFactory.createCommand("play"));
		toolbarButtons.get("record").addActionListener(commandsFactory.createCommand("record"));
		toolbarButtons.get("replay").addActionListener(commandsFactory.createCommand("replay"));
		
		// TEXT AREA
		textArea.addCaretListener(commandsFactory.createUpdateSelectionCommand());
		lines.addComponentListener(this);
	}
	
	/**
	 * Updates the line numbering next (left) to the text.
	 */
	private void updateLines() {
		int lineNum = textArea.getSize().height;
		
		//if there is open file
		if (toolbarButtons.get("close").isEnabled()) {
			StringBuilder totalLines = new StringBuilder();
			
			for (int i=1; i <= lineNum; i++) {
				totalLines.append(String.valueOf(i));
				totalLines.append("\n");
			}
			lines.setText(totalLines.toString());
		}else {
			lines.setText("");
		}
		lines.setCaretPosition(0);
	}
	

	
	// run
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new AdvancedTextToSpeechView().getJFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
