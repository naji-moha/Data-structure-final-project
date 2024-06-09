import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main {
    private JTextField Title;
    private JTextField DueTime;
    private JButton editButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton sortButton;
    private JPanel mainPanel;
    private JList<Task> taskList;
    private DefaultListModel<Task> listModel;
    private ArrayList<Task> tasks;

    public Main() {
        JFrame frame = new JFrame("YOUR TODO LIST MANAGER");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.PINK); 
        frame.setContentPane(mainPanel);

        frame.setVisible(true);
        ImageIcon image = new ImageIcon(getClass().getResource("/icon.png"));
        frame.setIconImage(image.getImage());

        Title = new JTextField();
        DueTime = new JTextField();
        addButton = new JButton("Add Task");
        editButton = new JButton("Edit Task");
        deleteButton = new JButton("Delete Task");
        sortButton = new JButton("Sort Tasks");

        addButton.addActionListener(new ButtonListener());
        editButton.addActionListener(new ButtonListener());
        deleteButton.addActionListener(new ButtonListener());
        sortButton.addActionListener(new ButtonListener());

        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setLayoutOrientation(JList.VERTICAL);
        taskList.setCellRenderer(new TaskCellRenderer());
        JScrollPane scrollPane = new JScrollPane(taskList);

        tasks = new ArrayList<>();

        
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.setBackground(Color.PINK);
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(Title);
        inputPanel.add(new JLabel("Due Time (yyyy-MM-dd HH:mm):"));
        inputPanel.add(DueTime);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.setBackground(Color.PINK);
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortButton);

        JPanel toolsPanel = new JPanel(new BorderLayout());
        toolsPanel.add(inputPanel, BorderLayout.NORTH);
        toolsPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(toolsPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                taskList.repaint();
            }
        });
        timer.start();
    }

    private class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addButton) {
                String taskTitle = Title.getText().trim();
                String dueTime = DueTime.getText().trim();
                if (!taskTitle.isEmpty() && !dueTime.isEmpty()) {
                    int taskNumber = listModel.size() + 1;
                    listModel.addElement(new Task(taskNumber + ". " + taskTitle, dueTime));
                    tasks.add(new Task(taskTitle, dueTime));
                    Title.setText("");
                    DueTime.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "haha enter a title and due time!");
                }
            } else if (e.getSource() == editButton) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String editedTask = JOptionPane.showInputDialog(null, "Edit Task:", listModel.getElementAt(selectedIndex).toString());
                    if (editedTask != null && !editedTask.isEmpty()) {
                        Task task = listModel.getElementAt(selectedIndex);
                        task.setTitle(editedTask.split(" - Due: ")[0]);
                        task.setDueTime(editedTask.split(" - Due: ")[1]);
                        taskList.repaint();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Select a task to update..");
                }
            } else if (e.getSource() == deleteButton) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    tasks.remove(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(null, "Click the task you want to delete!");
                }
            } else if (e.getSource() == sortButton) {
                sortTasks();
                refreshTaskList();
            }
        }
    }

    private void sortTasks() {
        if (tasks.size() > 1) {
            mergeSort(tasks, 0, tasks.size() - 1);
        }
    }

    private void mergeSort(ArrayList<Task> tasks, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(tasks, left, middle);
            mergeSort(tasks, middle + 1, right);
            merge(tasks, left, middle, right);
        }
    }

    private void merge(ArrayList<Task> tasks, int left, int middle, int right) {
        int n1 = middle - left + 1;
        int n2 = right - middle;

        ArrayList<Task> leftArray = new ArrayList<>();
        ArrayList<Task> rightArray = new ArrayList<>();

        for (int i = 0; i < n1; i++) {
            leftArray.add(tasks.get(left + i));
        }
        for (int i = 0; i < n2; i++) {
            rightArray.add(tasks.get(middle + 1 + i));
        }

        int i = 0, j = 0;
        int k = left;
        while (i < n1 && j < n2) {
            if (leftArray.get(i).getDueTime().compareTo(rightArray.get(j).getDueTime()) <= 0) {
                tasks.set(k, leftArray.get(i));
                i++;
            } else {
                tasks.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            tasks.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            tasks.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }

    private void refreshTaskList() {
        listModel.clear();
        for (Task task : tasks) {
            listModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    private static class Task {
        private String title;
        private String dueTime;

        public Task(String title, String dueTime) {
            this.title = title;
            this.dueTime = dueTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDueTime() {
            return dueTime;
        }

        public void setDueTime(String dueTime) {
            this.dueTime = dueTime;
        }

        @Override
        public String toString() {
            return title + " - Due: " + dueTime;
        }
    }

    private static class TaskCellRenderer extends DefaultListCellRenderer {
        private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Task) {
                Task task = (Task) value;
                label.setText(task.toString());

                try {
                    Date dueDate = dateFormat.parse(task.getDueTime());
                    Date now = new Date();
                    if (dueDate.before(now)) {
                        label.setBackground(Color.RED);
                    } else {
                        label.setBackground(Color.GREEN);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return label;
        }
    }
}
