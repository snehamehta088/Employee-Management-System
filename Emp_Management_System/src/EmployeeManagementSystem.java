import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class EmployeeManagementSystem extends JFrame {

    // Colors
    private static final Color DARK_BLUE = new Color(25, 55, 95);
    private static final Color BLUE = new Color(45, 100, 170);
    private static final Color LIGHT_BLUE = new Color(235, 243, 252);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(40, 40, 40);

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField departmentField;
    private JTextField salaryField;
    private JTextField searchField;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    private EmployeeDAO employeeDAO;

    public EmployeeManagementSystem() {

        employeeDAO = new EmployeeDAO();

        setTitle("Employee Management System");
        setSize(1050, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main background
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(LIGHT_BLUE);
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        );

        // ================= HEADER =================

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_BLUE);
        headerPanel.setBorder(
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        );

        JLabel title = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(title, BorderLayout.CENTER);

        // ================= SEARCH =================

        JPanel searchPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 10, 8)
        );

        searchPanel.setBackground(WHITE);

        JLabel searchLabel = new JLabel("Search Employee:");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 14));
        searchLabel.setForeground(TEXT_COLOR);

        searchField = new JTextField(25);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton searchButton = createButton("Search", BLUE);
        JButton showAllButton = createButton("Show All", DARK_BLUE);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(showAllButton);

        JPanel topPanel = new JPanel(new BorderLayout(0, 8));
        topPanel.setBackground(LIGHT_BLUE);

        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // ================= FORM =================

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(WHITE);

        formPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BLUE),
                        "Employee Details",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 15),
                        DARK_BLUE
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Name
        addFormRow(
                formPanel,
                gbc,
                0,
                "Name:",
                nameField = new JTextField(17)
        );

        // Email
        addFormRow(
                formPanel,
                gbc,
                1,
                "Email:",
                emailField = new JTextField(17)
        );

        // Phone
        addFormRow(
                formPanel,
                gbc,
                2,
                "Phone:",
                phoneField = new JTextField(17)
        );

        // Department
        addFormRow(
                formPanel,
                gbc,
                3,
                "Department:",
                departmentField = new JTextField(17)
        );

        // Salary
        addFormRow(
                formPanel,
                gbc,
                4,
                "Salary:",
                salaryField = new JTextField(17)
        );

        // ================= TABLE =================

        String[] columns = {
                "ID",
                "Name",
                "Email",
                "Phone",
                "Department",
                "Salary"
        };

        tableModel = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        employeeTable = new JTable(tableModel);

        employeeTable.setRowHeight(30);
        employeeTable.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        employeeTable.setSelectionBackground(
                new Color(210, 225, 245)
        );

        employeeTable.setSelectionForeground(TEXT_COLOR);

        employeeTable.setGridColor(
                new Color(210, 210, 210)
        );

        JTableHeader tableHeader = employeeTable.getTableHeader();

        tableHeader.setBackground(DARK_BLUE);
        tableHeader.setForeground(WHITE);
        tableHeader.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JScrollPane scrollPane = new JScrollPane(employeeTable);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(BLUE),
                        "Employee Records",
                        0,
                        0,
                        new Font("Arial", Font.BOLD, 15),
                        DARK_BLUE
                )
        );

        // ================= CENTER =================

        JPanel centerPanel = new JPanel(
                new BorderLayout(15, 0)
        );

        centerPanel.setBackground(LIGHT_BLUE);

        centerPanel.add(formPanel, BorderLayout.WEST);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // ================= BUTTONS =================

        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 15, 10)
        );

        buttonPanel.setBackground(LIGHT_BLUE);

        JButton addButton =
                createButton("Add Employee", BLUE);

        JButton updateButton =
                createButton("Update", new Color(70, 120, 70));

        JButton deleteButton =
                createButton("Delete", new Color(180, 65, 65));

        JButton clearButton =
                createButton("Clear", new Color(100, 100, 100));

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ================= ACTION LISTENERS =================

        addButton.addActionListener(e -> addEmployee());

        updateButton.addActionListener(e -> updateEmployee());

        deleteButton.addActionListener(e -> deleteEmployee());

        clearButton.addActionListener(e -> clearFields());

        searchButton.addActionListener(e -> searchEmployees());

        showAllButton.addActionListener(e -> loadEmployees());

        // ================= TABLE SELECTION =================

        employeeTable.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {

                        int row =
                                employeeTable.getSelectedRow();

                        if (row != -1) {

                            nameField.setText(
                                    tableModel.getValueAt(
                                            row, 1
                                    ).toString()
                            );

                            emailField.setText(
                                    tableModel.getValueAt(
                                            row, 2
                                    ).toString()
                            );

                            phoneField.setText(
                                    tableModel.getValueAt(
                                            row, 3
                                    ).toString()
                            );

                            departmentField.setText(
                                    tableModel.getValueAt(
                                            row, 4
                                    ).toString()
                            );

                            salaryField.setText(
                                    tableModel.getValueAt(
                                            row, 5
                                    ).toString()
                            );
                        }
                    }
                });

        add(mainPanel);

        loadEmployees();
    }

    // ================= FORM ROW =================

    private void addFormRow(
            JPanel panel,
            GridBagConstraints gbc,
            int row,
            String labelText,
            JTextField field
    ) {

        JLabel label = new JLabel(labelText);

        label.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        label.setForeground(TEXT_COLOR);

        gbc.gridx = 0;
        gbc.gridy = row;

        panel.add(label, gbc);

        field.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        gbc.gridx = 1;

        panel.add(field, gbc);
    }

    // ================= BUTTON STYLE =================

    private JButton createButton(
            String text,
            Color background
    ) {

        JButton button = new JButton(text);

        button.setBackground(background);
        button.setForeground(WHITE);

        button.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setPreferredSize(
                new Dimension(120, 35)
        );

        return button;
    }

    // ================= ADD EMPLOYEE =================

    private void addEmployee() {

        try {

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String department =
                    departmentField.getText().trim();

            double salary =
                    Double.parseDouble(
                            salaryField.getText().trim()
                    );

            Employee employee = new Employee(
                    0,
                    name,
                    email,
                    phone,
                    department,
                    salary
            );

            employeeDAO.addEmployee(employee);

            JOptionPane.showMessageDialog(
                    this,
                    "Employee added successfully!"
            );

            clearFields();
            loadEmployees();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid salary.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= UPDATE =================

    private void updateEmployee() {

        int row =
                employeeTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first."
            );

            return;
        }

        try {

            int id = Integer.parseInt(
                    tableModel.getValueAt(
                            row, 0
                    ).toString()
            );

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();

            String department =
                    departmentField.getText().trim();

            double salary =
                    Double.parseDouble(
                            salaryField.getText().trim()
                    );

            Employee employee = new Employee(
                    id,
                    name,
                    email,
                    phone,
                    department,
                    salary
            );

            employeeDAO.updateEmployee(employee);

            JOptionPane.showMessageDialog(
                    this,
                    "Employee updated successfully!"
            );

            clearFields();
            loadEmployees();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid salary.",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    // ================= DELETE =================

    private void deleteEmployee() {

        int row =
                employeeTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an employee first."
            );

            return;
        }

        int id = Integer.parseInt(
                tableModel.getValueAt(
                        row, 0
                ).toString()
        );

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this employee?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice ==
                JOptionPane.YES_OPTION) {

            employeeDAO.deleteEmployee(id);

            JOptionPane.showMessageDialog(
                    this,
                    "Employee deleted successfully!"
            );

            clearFields();
            loadEmployees();
        }
    }

    // ================= SEARCH =================

    private void searchEmployees() {

        String keyword =
                searchField.getText().trim();

        if (keyword.isEmpty()) {

            loadEmployees();

            return;
        }

        tableModel.setRowCount(0);

        List<Employee> employees =
                employeeDAO.searchEmployees(keyword);

        for (Employee employee : employees) {

            Object[] row = {
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getPhone(),
                    employee.getDepartment(),
                    employee.getSalary()
            };

            tableModel.addRow(row);
        }
    }

    // ================= LOAD EMPLOYEES =================

    private void loadEmployees() {

        tableModel.setRowCount(0);

        List<Employee> employees =
                employeeDAO.getAllEmployees();

        for (Employee employee : employees) {

            Object[] row = {
                    employee.getId(),
                    employee.getName(),
                    employee.getEmail(),
                    employee.getPhone(),
                    employee.getDepartment(),
                    employee.getSalary()
            };

            tableModel.addRow(row);
        }
    }

    // ================= CLEAR =================

    private void clearFields() {

        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        departmentField.setText("");
        salaryField.setText("");

        employeeTable.clearSelection();
    }

    // ================= MAIN =================

    public static void main(String[] args) {

        DatabaseSetup.createTable();

        SwingUtilities.invokeLater(() -> {

            EmployeeManagementSystem app =
                    new EmployeeManagementSystem();

            app.setVisible(true);
        });
    }
}