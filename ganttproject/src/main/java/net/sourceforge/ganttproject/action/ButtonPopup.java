package net.sourceforge.ganttproject.action;

import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.task.TaskManager;

import java.awt.event.ActionEvent;

public class ButtonPopup extends GPAction {
  private final TaskManager myTaskManager;

  private final UIConfiguration myUIConfiguration;

  private final UIFacade myUiFacade;

  public ButtonPopup(TaskManager taskManager, UIConfiguration uiConfiguration, UIFacade uiFacade) {
    super("criticalPath.toggle");
    myTaskManager = taskManager;
    myUIConfiguration = uiConfiguration;
    myUiFacade = uiFacade;
  }

  @Override
  protected String getIconFilePrefix() {
    return "Button";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //Pop up
    //where is where something is done when clicked
  }

  @Override
  public String getLocalizedName() {
    return "Button";
  }

  private boolean isOn() {
    return myUIConfiguration == null ? false : myUIConfiguration.isCriticalPathOn();
  }
}
