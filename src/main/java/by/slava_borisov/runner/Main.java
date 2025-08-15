package by.slava_borisov.runner;

import by.slava_borisov.entity.Client;
import by.slava_borisov.entity.Order;
import by.slava_borisov.entity.Status;
import by.slava_borisov.service.ClientService;
import by.slava_borisov.service.CouponService;
import by.slava_borisov.service.OrderService;
import by.slava_borisov.service.ProfileService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner;
    private static ClientService clientService;
    private static CouponService couponService;
    private static OrderService orderService;
    private static ProfileService profileService;

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext("by.slava_borisov")) {

            clientService = context.getBean(ClientService.class);
            couponService = context.getBean(CouponService.class);
            orderService = context.getBean(OrderService.class);
            profileService = context.getBean(ProfileService.class);

            scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                showMenu();
                int choice = getChoice();
                exit = processChoice(choice);
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nГлавное меню:");
        System.out.println("1. Добавить клиента");
        System.out.println("2. Удалить клиента");
        System.out.println("3. Редактировать профиль");
        System.out.println("4. Добавить заказ");
        System.out.println("5. Редактировать купоны");
        System.out.println("6. Найти заказы");
        System.out.println("7. Вывести всех клиентов с заказами");
        System.out.println("8. Выход");
        System.out.print("Введите номер команды: ");
    }

    private static int getChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private static boolean processChoice(int choice) {
        switch (choice) {
            case 1 -> addClient();
            case 2 -> removeClient();
            case 3 -> editProfile();
            case 4 -> addOrder();
            case 5 -> editCoupon();
            case 6 -> findOrders();
            case 7 -> showClientsWithOrders();
            case 8 -> {
                System.out.println("Выход из программы...");
                return true;
            }
            default -> System.out.println("Неверный выбор. Попробуйте снова");
        }
        return false;
    }

    private static void addClient() {
        System.out.print("Введите имя клиента: ");
        String name = scanner.nextLine();

        System.out.print("Введите email клиента: ");
        String email = scanner.nextLine();

        System.out.print("Введите адрес профиля: ");
        String address = scanner.nextLine();

        System.out.print("Введите телефон профиля: ");
        String phone = scanner.nextLine();

        clientService.addClient(name, email, address, phone);
        System.out.println("Клиент успешно добавлен!");
    }

    private static void removeClient() {
        System.out.print("Введите ID клиента для удаления: ");
        Long clientId = scanner.nextLong();
        scanner.nextLine();

        try {
            clientService.removeClient(clientId);
            System.out.println("Клиент с id " + clientId + " успешно удален");
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void editProfile() {
        System.out.print("Введите ID профиля для редактирования: ");
        Long profileId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите новый адрес (оставьте пустым, если не нужно изменять): ");
        String newAddress = scanner.nextLine();
        if (newAddress.isEmpty()) newAddress = null;

        System.out.print("Введите новый телефон (оставьте пустым, если не нужно изменять): ");
        String newPhone = scanner.nextLine();
        if (newPhone.isEmpty()) newPhone = null;

        profileService.updateProfile(profileId, newAddress, newPhone);
        System.out.println("Профиль успешно отредактирован!");
    }

    private static void addOrder() {
        System.out.print("Введите ID клиента: ");
        Long clientId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите дату заказа (в формате YYYY-MM-DD): ");
        String orderDateStr = scanner.nextLine();
        LocalDate orderDate = LocalDate.parse(orderDateStr);

        System.out.print("Введите сумму заказа: ");
        BigDecimal totalAmount = new BigDecimal(scanner.nextLine());

        System.out.print("Введите статус заказа (PENDING/CONFIRMED/PROCESSING/COMPLETED/CANCELLED): ");
        String statusStr = scanner.nextLine();
        Status status = Status.valueOf(statusStr.toUpperCase());

        Client client = clientService.getClientById(clientId);
        if (client != null) {
            orderService.addOrder(client, orderDate, totalAmount, status);
            System.out.println("Заказ успешно добавлен!");
        } else {
            System.out.println("Клиент с таким ID не найден!");
        }
    }

    private static void editCoupon() {
        System.out.print("Введите ID купона для редактирования: ");
        Long couponId = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Введите новый код купона: ");
        String newCode = scanner.nextLine();

        System.out.print("Введите новую скидку (в виде числа, например, 10.5): ");
        BigDecimal newDiscount = new BigDecimal(scanner.nextLine());

        couponService.updateCoupon(couponId, newCode, newDiscount);
        System.out.println("Купон успешно отредактирован!");
    }

    private static void findOrders() {
        System.out.print("Введите дату заказа (в формате YYYY-MM-DD, или нажмите Enter, чтобы пропустить): ");
        String orderDateStr = scanner.nextLine();
        LocalDate orderDate = (orderDateStr.isEmpty()) ? null : LocalDate.parse(orderDateStr);

        System.out.print("Введите сумму заказа (или нажмите Enter, чтобы пропустить): ");
        String totalAmountStr = scanner.nextLine();
        BigDecimal totalAmount = (totalAmountStr.isEmpty()) ? null : new BigDecimal(totalAmountStr);

        System.out.print("Введите статус заказа " +
                         "(PENDING/CONFIRMED/PROCESSING/COMPLETED/CANCELLED, или нажмите Enter, чтобы пропустить): ");
        String statusStr = scanner.nextLine();
        Status status = (statusStr.isEmpty()) ? null : Status.valueOf(statusStr.toUpperCase());

        List<Order> orders = orderService.findOrders(orderDate, totalAmount, status);

        if (orders.isEmpty()) {
            System.out.println("Заказы не найдены.");
        } else {
            System.out.println("Найденные заказы:");
            for (Order order : orders) {
                System.out.println("ID: " + order.getId() +
                                   ", Дата: " + order.getOrderDate() +
                                   ", Сумма: " + order.getTotalAmount() +
                                   ", Статус: " + order.getStatus());
            }
        }
    }

    private static void showClientsWithOrders() {
        System.out.println("Загрузка клиентов с заказами...");
        List<Client> clients = orderService.findClientWithOrders();

        if (clients.isEmpty()) {
            System.out.println("Клиенты не найдены.");
        } else {
            System.out.println("Клиенты с заказами:");
            System.out.println("=".repeat(40));
            for (Client client : clients) {
                System.out.println("Клиент: " + client.getName() + " (ID: " +
                                   client.getId() + ", Email: " + client.getEmail() + ")");
                if (client.getOrders().isEmpty()) {
                    System.out.println("  Заказы: нет заказов");
                } else {
                    for (Order order : client.getOrders()) {
                        System.out.println("  Заказ ID: " + order.getId() +
                                           ", Дата: " + order.getOrderDate() +
                                           ", Сумма: " + order.getTotalAmount() +
                                           ", Статус: " + order.getStatus());
                    }
                }
                System.out.println("-".repeat(40));
            }
        }
    }
}