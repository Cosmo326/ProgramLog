package com.adamcosentino.ProgramLog.Log;

import com.adamcosentino.ProgramLog.Utilities.DisplayLevel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

/**
 * ====================================================
 * <p>
 * Author: Adam Cosentino
 * Date: Jul 30, 2016  14:09
 * Project: ProgramLog
 * Project Description:
 * Class Description:
 * <p>
 * ====================================================
 */
public final class LogViewer implements ComponentListener, ILogViewer{
  
  private static LogViewer instance;
  
  private int width = 700;
  private int height = 400;
  
  private JFrame frame;
  private ProgramLog log;
  private JButton clearLog, load;
  private JScrollPane scrollPane;
  private JCheckBox[] levels;
  private JTextArea display;
  
  private LogViewer(){
    log = ProgramLog.getInstance();
    log.registerLogViewer(this, DisplayLevel.INFO.Value | DisplayLevel.WARN.Value | DisplayLevel.ERR.Value | DisplayLevel.FATAL.Value);
    
    frame = new JFrame("Log Viewer");
    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    frame.setResizable(true);
    frame.addComponentListener(this);
    frame.setContentPane(new JLayeredPane());
    
    load = new JButton("Load Log File");
    load.addActionListener((ActionEvent e) -> {
      JFileChooser jfc = new JFileChooser();
      jfc.setFileFilter(new FileNameExtensionFilter("Log Files",".log"));
      int retVal = jfc.showDialog(null,"Open");
      if(retVal == JFileChooser.APPROVE_OPTION){
        File file = jfc.getSelectedFile();
        System.out.println(file.getName());
      }
    });
    frame.add(load);
    
    clearLog = new JButton("Clear Log");
    clearLog.addActionListener((ActionEvent e) -> {
      display.setText("");
    });
    frame.add(clearLog);
    
    levels = new JCheckBox[DisplayLevel.values().length];
    for(int i = 0; i < levels.length; i++){
      final int temp = i;
      levels[i] = new JCheckBox(DisplayLevel.values()[i].Label);
      levels[i].setToolTipText(DisplayLevel.values()[i].Description);
      levels[i].setSelected((log.getLevel(this) & DisplayLevel.values()[i].Value) == DisplayLevel.values()[i].Value);
      levels[i].addActionListener((ActionEvent e) -> {
        log.toggleLevel(this, DisplayLevel.values()[temp].Value);
      });
      frame.add(levels[i]);
    }
    
    display = new JTextArea("");
    display.setEditable(false);
    display.setBackground(Color.BLACK);
    display.setForeground(Color.WHITE);
    scrollPane = new JScrollPane();
    scrollPane.setViewportView(display);
    frame.add(scrollPane);
  
    Dimension dim = new Dimension(width, height);
    frame.setMinimumSize(dim);
    frame.setPreferredSize(dim);
    frame.pack();
    toggleLogDisplay();
  }
  
  public static LogViewer getInstance(){
    if(instance == null) instance = new LogViewer();
    return instance;
  }
  
  public void toggleLogDisplay() { frame.setVisible(!frame.isVisible()); }
  
  
  public static void main(String[] args){
    LogViewer.getInstance();
  }
  
  @Override
  public void componentResized(ComponentEvent e) {
    width = e.getComponent().getWidth();
    height = e.getComponent().getHeight();
    
    load.setBounds(width - 150,5,150,25);
    clearLog.setBounds(5,5,150,25);
    scrollPane.setBounds(5,60,width - 10, height - 85);
    for(int i = 0; i < levels.length; i++){
      levels[i].setBounds(5 + i * 120, 30, 120, 25);
    }
  }
  
  @Override
  public void componentMoved(ComponentEvent e) {}
  
  @Override
  public void componentShown(ComponentEvent e) {}
  
  @Override
  public void componentHidden(ComponentEvent e) {}
  
  @Override
  public void updateLog(String log) {
    if(display != null) display.append(log);
  }
}
