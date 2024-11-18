# Soccer Player Management App

![spring-soccer-dnd_small](https://github.com/user-attachments/assets/da79f9a3-34b8-4096-bb98-81d48bfa8bb7)
<br/><br/>

**Soccer DnD Player Stats Management System**, designed to manage and track players' performance statistics over multiple seasons. The app provides RESTful APIs for handling players and their season-specific stats, offering seamless CRUD operations, robust validation, and detailed exception handling.

---

## **Features**
- **Player Management**:
  - Add, update, retrieve, and delete players.
  - Supports validation to ensure data integrity (e.g., checking for existing players before adding or updating records).

- **Player Stats Management**:
  - Track player performance for specific seasons, including goals scored.
  - Prevents duplicate stats entries for the same player and season via unique constraints.
  - Update existing stats or add new ones dynamically.
  
- **Exception Handling**:
  - Custom exception responses for missing players, invalid stats, and unexpected errors.

- **Custom Queries**:
  - Retrieve all player stats with a detailed breakdown.
  - Fetch specific stats based on player ID, name, or season year.

---

## **Tech Stack**
- **Backend**:
  - **Java 21**
  - **Spring Boot**: Framework for developing RESTful APIs.
  - **Spring Data JPA & Hibernate**: ORM for database interaction, mapping Java objects to database tables, and enabling seamless query handling.
  - **Lombok**: Simplifies boilerplate code with annotations.
  
- **Database**:
  - **H2 Database**: In-memory relational database for testing and development.

- **Testing**:
  - **JUnit 5**: Unit testing framework.
  - **MockMvc**: Simulates HTTP requests for controller testing.
  - **Mockito**: Mocks service dependencies to test specific behaviors.
  - **Parameterized Testing**: Dynamic test cases for diverse scenarios.

- **Other Libraries**:
  - **Jakarta Persistence API (JPA)**: Standardized ORM API supported by Hibernate.

---

## **Endpoints**
### Player Endpoints:
- `GET /api/players` - Fetch all players.
- `GET /api/players/{id}` - Fetch a player by ID.
- `POST /api/players/add` - Add a new player.
- `PUT /api/players/update` - Update player stats.
- `DELETE /api/players/delete/{id}` - Delete a player.

### Player Stats Endpoints:
- `GET /api/player-stats` - Retrieve all player stats.
- `GET /api/player-stats/{playerId}` - Retrieve stats for a specific player.
- `POST /api/player-stats/add` - Add player stats.
- `PUT /api/player-stats/update` - Update player stats.
- `DELETE /api/player-stats/delete` - Delete stats by player and season year.

[Download the Postman Collection](https://github.com/lboric/spring-soccer-dnd/blob/main/src/main/resources/spring-soccer-dnd.postman_collection.json)

---

## **How to Run**
1. Clone this repository:
   ```bash
   git clone https://github.com/lboric/spring-soccer-dnd.git
2. Run Sprint Boot app:
   ```bash
   ./mvnw spring-boot:run

---

<h2>Overall Coverage Summary </h2>
<table class="coverageStats">
  <tr>
    <th class="name">Package</th>
<th class="coverageStat 
">
  Class, %
</th>
<th class="coverageStat 
">
  Method, %
</th>
<th class="coverageStat 
">
  Line, %
</th>
  </tr>
  <tr>
    <td class="name">All classes</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (19/19)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (65/65)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (179/179)
  </span>
</td>
  </tr>
</table>

<br/>
<h2>Coverage Breakdown</h2>

<table class="coverageStats">
<tr>
  <th class="name  sortedAsc
">
<a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/index_SORT_BY_NAME_DESC.html">Package</a>  </th>
<th class="coverageStat 
">
  <a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/index_SORT_BY_CLASS.html">Class, %</a>
</th>
<th class="coverageStat 
">
  <a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/index_SORT_BY_METHOD.html">Method, %</a>
</th>
<th class="coverageStat 
">
  <a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/index_SORT_BY_LINE.html">Line, %</a>
</th>
</tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-1/index.html">com.lboric.soccerdnd</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (1/1)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-2/index.html">com.lboric.soccerdnd.controllers</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (13/13)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (36/36)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-3/index.html">com.lboric.soccerdnd.dtos</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (4/4)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (14/14)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-4/index.html">com.lboric.soccerdnd.entities</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (12/12)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-5/index.html">com.lboric.soccerdnd.exceptions</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (5/5)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (10/10)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (10/10)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-6/index.html">com.lboric.soccerdnd.models</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (4/4)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (13/13)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-7/index.html">com.lboric.soccerdnd.repositories</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (1/1)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (6/6)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-8/index.html">com.lboric.soccerdnd.services.impl</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (23/23)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (79/79)
  </span>
</td>
  </tr>
  <tr>
    <td class="name"><a href="https://github.com/lboric/spring-soccer-dnd/blob/main/htmlReport/ns-9/index.html">com.lboric.soccerdnd.utils</a></td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (2/2)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (5/5)
  </span>
</td>
<td class="coverageStat">
  <span class="percent">
    100%
  </span>
  <span class="absValue">
    (7/7)
  </span>
</td>
  </tr>
</table>
</div>


