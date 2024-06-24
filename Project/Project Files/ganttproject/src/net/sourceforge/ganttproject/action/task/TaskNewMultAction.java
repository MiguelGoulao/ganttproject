/*
GanttProject is an opensource project management tool.
Copyright (C) 2005-2011 GanttProject Team

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 3
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package net.sourceforge.ganttproject.action.task;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.action.GPAction;
import net.sourceforge.ganttproject.gui.GanttDialogMultTask;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.gui.UIUtil;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.TaskSelectionManager;
import net.sourceforge.ganttproject.task.TaskManager;

import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.Date;
import java.util.Calendar;

public class TaskNewMultAction extends GPAction {

  private final IGanttProject myProject;
  private final UIFacade myUiFacade;
  private final TaskSelectionManager selection;
  private final Calendar myCalendar;


  public TaskNewMultAction(IGanttProject project, TaskSelectionManager selectionManager, UIFacade uiFacade) {
    this(project, selectionManager, uiFacade, IconSize.MENU);
  }

  private TaskNewMultAction(IGanttProject project, TaskSelectionManager selectionManager, UIFacade uiFacade,
      IconSize size) {
    super("task.newMult", size);
    selection = selectionManager;
    myProject = project;
    myUiFacade = uiFacade;
    myCalendar = Calendar.getInstance();
  }

  @Override
  public GPAction withIcon(IconSize size) {
    return new TaskNewMultAction(myProject, selection, myUiFacade, size);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
      final GanttDialogMultTask pd = new GanttDialogMultTask();
      final TaskManager tskmng = myProject.getTaskManager();
      pd.show(myProject, myUiFacade);
      SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
          if(pd.getStart() == null || pd.getEnd() == null) {
            return;
          }
          Date i = pd.getStart();
          myCalendar.setTime(i);
          while (i.before(pd.getEnd())) {
            Task selectedTask = selection.getSelectedTasks().isEmpty() ? null : selection.getSelectedTasks().get(0);
            if(myCalendar.get(myCalendar.DAY_OF_WEEK) != myCalendar.SATURDAY && myCalendar.get(myCalendar.DAY_OF_WEEK) != myCalendar.SUNDAY) {
              Task newTask = tskmng.newTaskBuilder()
                      .withPrevSibling(selectedTask).withStartDate(i).build();
              myUiFacade.getTaskTree().startDefaultEditing(newTask);
            }
            myCalendar.add(Calendar.DATE, 1);
            i = myCalendar.getTime();
          }
        }
      });
  }

  protected TaskManager getTaskManager() {
    return myProject.getTaskManager();
  }

  protected UIFacade getUIFacade() {
    return myUiFacade;
  }

  @Override
  public TaskNewMultAction asToolbarAction() {
    TaskNewMultAction result = new TaskNewMultAction(myProject, selection, myUiFacade);
    result.setFontAwesomeLabel(UIUtil.getFontawesomeLabel(result));
    return result;
  }
}
