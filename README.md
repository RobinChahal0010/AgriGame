# 🌾 AgroCore - Autonomous Farming Simulation Engine

AgroCore is a Java-based farming simulation that demonstrates **multithreading, concurrency, object-oriented design, and Swing GUI development**. The project simulates an autonomous tractor managing a farm while users interact with the field through a graphical interface.

---

## 🚀 Features

- 🌱 Interactive farming grid using Java Swing
- 🚜 Autonomous tractor running in a separate thread
- 🌾 Crop growth simulation over time
- 💰 Virtual economy with wallet management
- 📋 Real-time system logs
- 🎨 Color-coded field visualization
- 🔄 Background nature simulation
- ⚡ Thread-safe shared resource handling

---

## 🛠 Tech Stack

- Java
- Java Swing
- Multithreading
- Concurrency
- OOP
- AtomicInteger
- Synchronization

---

## 📂 Project Structure

```
AgroCore/
│
├── app/
│   └── Main.java
│
├── grid/
│   └── FieldPatch.java
│
├── machinery/
│   └── AutonomousTractor.java
│
├── models/
│   └── Crop.java
│
└── README.md
```

---

## ⚙️ Simulation Workflow

```
User
 │
 ▼
Clicks Field
 │
 ▼
Plant Wheat
 │
 ▼
Wallet Deducts Money
 │
 ▼
Nature Thread
 │
 ▼
Crop Growth
 │
 ▼
Harvest Ready
 │
 ▼
Autonomous Tractor
 │
 ▼
Harvest Crop
 │
 ▼
Wallet Reward
```

---

## 🧵 Thread Architecture

### 🚜 Tractor Thread

Responsible for:

- Plowing land
- Harvesting mature crops
- Automating farm operations

Runs continuously in the background.

---

### 🌿 Nature Thread

Responsible for:

- Crop growth
- Updating plant progress
- Refreshing UI every few seconds

---

### 🖥 Swing Event Dispatch Thread (EDT)

Responsible for:

- Button clicks
- UI updates
- Event logging
- Wallet display

All UI updates are executed using:

```java
SwingUtilities.invokeLater(...)
```

to ensure thread safety.

---

## 🌱 Field States

| State | Description |
|--------|-------------|
| EMPTY | Unplowed land |
| PLOWED | Ready for planting |
| SEEDED | Crop is growing |
| READY_FOR_HARVEST | Ready to harvest |

---

## 💰 Economy System

- Initial Wallet Balance: **$100**
- Planting Wheat Cost: **$20**
- Harvesting rewards the player with money.
- Uses `AtomicInteger` for thread-safe balance updates.

---

## 🎨 GUI Components

### Header

- Project title
- Wallet balance

### Farm Grid

- 3×3 interactive farming field
- Clickable cells
- Dynamic colors

### System Logs

Displays events such as:

- Tractor activity
- Crop planting
- Harvest completion
- Errors
- Economy updates

---

## 🌾 Crop Lifecycle

```
EMPTY
   │
   ▼
PLOWED
   │
   ▼
SEEDED
   │
   ▼
Growing...
   │
   ▼
READY_FOR_HARVEST
   │
   ▼
Harvested
   │
   ▼
EMPTY
```

---

## 🔒 Concurrency Features

The project demonstrates several Java concurrency concepts:

- Thread creation
- Runnable interface
- AtomicInteger
- synchronized blocks
- Swing EDT
- Shared resource management

Example:

```java
synchronized (patch) {
    patch.plantCrop(...);
}
```

---

## ▶️ How to Run

Clone the repository:

```bash
git clone https://github.com/your-username/AgroCore.git
```

Navigate to the project:

```bash
cd AgroCore
```

Compile:

```bash
javac app/Main.java
```

Run:

```bash
java app.Main
```

---

## 📸 Preview

```
+-------------------------------------------------------------+
| AGROCORE FARM SIMULATOR          Wallet Balance: $100       |
+----------------------+------------------------------+
| [P] Ready            |                              |
| [S] Growing (2/3)    |      System Logs             |
| [H] Harvest Ready    |                              |
| [.] Empty            | Tractor ONLINE               |
| ...                  | Wheat Planted               |
+----------------------+------------------------------+
```

---

## 🎯 Learning Outcomes

This project demonstrates:

- Java Swing GUI development
- Object-Oriented Programming
- Multithreading
- Concurrency control
- Event-driven programming
- Simulation engine design
- Shared resource synchronization

---

## 🔮 Future Enhancements

- 🌧 Dynamic weather system
- 🚜 Multiple autonomous tractors
- 🌽 Multiple crop types
- 📈 Crop market pricing
- 💾 Save & load game
- 🗺 Larger farm grid
- ❤️ Crop health monitoring
- 🐛 Pest management
- 📊 Analytics dashboard
- 🤖 AI-based tractor path planning
- 🛰 IoT sensor integration

---

## 👨‍💻 Author

**Robinpreet Singh Chahal**

Backend-Focused Java Developer

Specializing in:

- Java
- Spring Boot
- System Design
- Multithreading
- Distributed Systems
- AI-powered Backend Applications

---

## ⭐ If you like this project

Give the repository a ⭐ and feel free to contribute!
