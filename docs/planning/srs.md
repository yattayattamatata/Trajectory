# Trajectory

**Team 6**

Andrew Marders, LauraJean Hill, Johnny Aristide, Madison Rodman

## Software Requirements Specification

---

## Table of Contents

1. [Purpose](#1-purpose)
2. [Scope](#2-scope)
3. [User characteristics](#3-user-characteristics)
   - 3.1 [Key users](#31-key-users)
   - 3.2 [Secondary users](#32-secondary-users)
   - 3.3 [Unimportant users](#33-unimportant-users)
4. [Product perspective](#4-product-perspective)
   - 4.1 [System Context](#41-system-context)
   - 4.2 [User interfaces](#42-user-interfaces)
   - 4.3 [Software interfaces](#43-software-interfaces)
   - 4.5 [Deployment requirements](#45-deployment-requirements)
5. [Assumptions and Dependencies](#5-assumptions-and-dependencies)
6. [Specific requirements](#6-specific-requirements)
   - 6.1 [System Functional Requirements](#61-system-functional-requirements)
   - 6.2 [Logical Database Requirements](#62-logical-database-requirements)
   - 6.3 [Software System Attributes](#63-software-system-attributes)
     - 6.3.1 [Usability](#631-usability)
     - 6.3.2 [Performance](#632-performance)
     - 6.3.3 [Reliability/Dependability](#633-reliabilitydependability)
     - 6.3.4 [Security](#634-security)
     - 6.3.5 [Maintainability](#635-maintainability)

---

## 1 Purpose

Trajectory is a financial tool built to help inform and assess users on long term effects on their financial future. Trajectory is built to track budgets, investments, and debts. This will allow users to have a grasp of their future outcomes. By bringing these different areas together, Trajectory gives users a clearer understanding of their current financial situation and how their choices today may affect their future.

This new system is a free system that allows users to visualize and understand how small decisions can affect their long-term financial goals. Most currently available tools are not free and focus solely on creating budgets. This tool goes a step further, allowing users to not only create a budget but also to understand why (visually) the decisions they make today have such a strong impact on their futures.

## 2 Scope

The name of the system is Trajectory. The goal of Trajectory is to provide users with a better understanding of their finances and how their financial decisions can affect them in the future. Users will be able to create a budget to see where their money is going and how much they will have available for investments, savings, and debt. Users will also be able to enter their debt balance and APR to see how much their debt will cost them over time and how different payment amounts will affect the amount of interest they pay. Trajectory will also allow users to enter their savings or investment balance, contributions, and APY to visualize how their savings and investments could grow over time. Users will be able to compare these different financial scenarios through graphs to better understand their results.

Trajectory can be used by individuals who are looking for a way to better manage and understand their personal finances. Instead of having to use different resources for budgeting, debt, savings, and investments, users will be able to access this information in one place. Trajectory will allow users to compare different financial choices and see how those choices may affect them over time. This can benefit users by making financial information easier to understand and helping them make more informed decisions with their money.

## 3 User characteristics

Trajectory is intended for users with different levels of financial knowledge and different levels of experience using technology. With some users may already understand budgeting, debt, and investing, while others may have very little experience with those topics. Because of that, the system should be simple enough for beginners to use while still being useful for people who are more financially experienced.

### 3.1 Key users

Key users are the people who will regularly use Trajectory to manage their personal finances and make decisions about budgeting, debt, and investments. Their feedback is the most important because they will be the main users of the system, and they will enter and update their income, expenses, debts, and investments. They will use the system to create a budget, track financial information, and view investment growth projections. Their subject matter experience can range from beginner to journeyman when it comes to personal finance, and they may have very little knowledge about APR, APY, budgeting, or investing, while others may already understand these concepts. Their technological experience can also range from beginner to journeyman. The system should only require basic experience with using a computer, entering information into forms, and navigating between different screens.

### 3.2 Secondary users

These users are people who may use Trajectory from time to time, but they are not the main users of the system. These users could include family members helping someone with their finances, students learning about personal finance, or someone reviewing another person's financial plan. Secondary users may review financial information, help enter data, or use the budgeting, debt, and investment tools for learning or comparison. Their subject matter experience may range from novice to master depending on their experience with personal finance. Some may have very little financial knowledge, while others may have more experience with budgeting or investing. Their technological experience may range from novice to journeyman. The application should be simple enough that someone who does not use it often can still understand how to navigate it.

### 3.3 Unimportant users

These users are not expected to regularly use Trajectory, and their needs have the lowest priority. This can include unauthorized users, people who only use the program once, or people who may attempt to misuse the system. These users do not have an important role in the normal use of Trajectory, and their access should be limited to whatever functions they are allowed to use. Their financial knowledge can range from novice to master, but their level of financial knowledge does not strongly affect the design of the system because they are not intended users.

## 4 Product perspective

### 4.1 System Context

Trajectory is a standalone system and does not rely on any other systems. The user interacts with the GUI to enter and view their financial information. The GUI passes the user's entries to the calculation logic, which processes the financial data. The local backend stores the user's information directly on their computer. All parts of Trajectory operate locally on the user's computer without requiring an external server or system.

### 4.2 User interfaces

The Trajectory user interface must allow users to easily enter, view, and manage their financial information. The interface will include:

- Error messages for data validation next to the relevant fields.
- Menu options that allow users to save and load their financial information.
- Screens that support both text and graphs/charts.
- Data visualizations for debts and investments.
- Separate pages for an overview, budget management, debt management, and investment management.
- Navigation that allows users to easily move between the different pages.

### 4.3 Software interfaces

Trajectory does not require any external software interfaces specified by the customer. It is a standalone desktop application that operates on the supported operating systems listed in A.2 and Section 4.5. All user data is stored locally on the user's computer.

### 4.5 Deployment requirements

Trajectory will be deployed as a standalone desktop application for Windows and Mac computers. The system will support Windows 10 or later and macOS 11 or later. Approximately 500 MB of available storage will be required for the application and its locally stored data. Trajectory will include its own Java runtime, so users will not need to install Java separately. Since Trajectory operates as a standalone system, no external server or backend setup will be required. User data will be created and stored locally on the user's computer.

## 5 Assumptions and Dependencies

**A.1 Computer Access:** Users are assumed to have access to a desktop or laptop that can run Trajectory.

**A.2 Supported Operating System:** Users are expected to use Windows or macOS. Supporting other operating systems or mobile devices would require making changes to the requirements and need for additional testing.

**A.3 Accurate Information:** Users are responsible for entering accurate financial information. Trajectory can check whether an entry is valid, but it cannot confirm whether the user's information matches their actual financial account(s).

**A.4 Manual Entry:** Trajectory assumes all users will enter their financial information manually. Connecting to bank accounts or other financial applications would require additional features beyond the project's scope.

**A.5 Currency:** All financial amounts entered are assumed to be in US dollars. Trajectory does not support other currencies since it would require changing data entries, calculations, and displayed results.

**A.6 Estimated Growth:** Savings and investment projections will use the APY or estimated annual return entered by the user. These projections are intended for long-term planning, and Trajectory will not continuously track changes to the stock market.

**A.7 Projection Assumptions:** Debt and investment projections assume that the entered rates, monthly payments, and monthly contributions remain unchanged during the projection period. Changes to these values will require the user to recalculate the projection.

**A.8 Data Storage:** Saving information depends on the user, ensuring the computer has the available storage and permissions to save the data required for the application.

## 6 Specific requirements

### 6.1 System Functional Requirements

**R.1** The system shall allow users to create and select profiles containing a profile name. Each profile containing financial information shall remain separate.

**R.2** The system shall allow users to add, view, edit, and delete debts containing a name, starting balance, APR, and minimum monthly payment.

**R.3** The debt list shall display each debt's name and entered balance in the selected profile's debt list.

**R.4** The system shall accept a proposed monthly payment for a selected debt and display the estimated payoff time in months, total interest paid, and remaining balance for each month. If payoff takes more than 100 years, the system shall inform the user that the debt cannot be paid off in the maximum projection period. If the payment does not exceed the monthly interest on a positive balance, the system shall explain that the payment is insufficient to pay off the debt.

**R.5** The system shall allow users to add, view, edit, and delete savings or investment accounts containing a name, starting balance, APY or estimated annual return, and monthly contribution.

**R.5.1** The system may allow users to enter an expected retirement date and use it as the endpoint for a savings or investment projection.

**R.6** The system shall calculate growth over a user's period of choice (1-100 years) and display the ending balance, total contributions, and estimated earnings during that period. Users shall be able to change their rate or contribution and then recalculate.

**R.7** The system shall allow users to create, view, edit, and delete monthly budgets containing a name, month, and year. Users shall be able to add, edit, and delete entries containing a description, category, amount, and income or expense designation.

**R.8** The system shall display total income, total expenses, and the remaining amount, updating these totals whenever budget entries change.

**R.9** The system shall save and restore profile information, budgets, debts, investments, and financial scenarios between sessions.

**R.10** The system shall report whether saving succeeded and retain entered information if saving fails so users can retry.

**R.11** The system shall reject blank required fields, nonnumeric financial inputs, and negative balances, rates, payments, or contributions.

**R.11.1** The system shall require proposed debt payments are greater than zero, and projection periods shall be expressed as whole numbers (1 to 100).

**R.12** The system shall identify invalid fields, explain the errors, and retain other valid entries.

**R.13** The system shall display debt and investment projections in graphs and tables. Graphs shall identify the debt or account, label the time and balance axes, and show monetary values in U.S. dollars.

### 6.2 Logical Database Requirements

**R.14** The system shall store the following information:

| Data Entity | Information Stored |
|---|---|
| User Profile | Unique identifier and profile name. |
| Debt | Unique identifier, profile identifier, name, starting balance, APR, and minimum payment. |
| Savings or Investment | Unique identifier, profile identifier, name, starting balance, APY or estimated annual return, and monthly contribution. |
| Budget | Unique identifier, profile identifier, name, month, and year. |
| Budget entry | Unique identifier, budget identifier, description, category, amount, and income/expense designation. |
| Scenario | Unique identifier, profile identifier, debt or investment identifier, and the input values used for the projection. |
| Scenario Results | Scenario identifier, monthly projected balances, debt payoff time and total interest, or investment ending balance, total contributions, and estimated earnings. |

**R.15** The system shall associate each debt, investment, budget, and scenario with one user profile. Each budget entry shall belong to one budget, and each scenario result shall belong to that scenario and reflect its saved inputs.

**R.16** The system shall assign unique identifiers to records and prevent referring to nonexistent records. Changes to scenario inputs all require recalculation before updated results will be shown and saved.

**R.17** The system shall preserve entered monetary amounts to the cent when saving and loading. Failed saves shall not overwrite previously saved information with incomplete data.

**R.18** The system may support retirement planning by storing an expected retirement date with the user profile and retaining the date used in each retirement-based scenario.

**R.19** The system shall check that saved and loaded information contains the required fields and correct data types and follows the validation rules in R.11. Invalid records shall produce an error message rather than being used in calculations.

### 6.3 Software System Attributes

#### 6.3.1 Usability

**UR.1** The system shall display monetary amounts with currency symbols, thousand separators, and exactly 2 decimal places.

**UR.2** The system shall display interest rates as percentages with up to 2 decimal places.

**UR.3** The system shall display error messages beside any invalid or empty fields within 1 second of the user's submission.

**UR.4** The system shall require confirmation prior to deleting information (profile, debt, investment, budget, budget entry) and shall not delete information if the operation is cancelled.

**UR.5** The system shall allow a debt, investment, or budget to be created from a single input screen.

**UR.6** The system shall display a message when presenting projections, clarifying that results are estimates based on entered values and not financial advice.

**UR.7** The system shall allow a new user to add a new debt or investment and view the projection within 5 minutes, verified by a small group of 4-5 testers.

#### 6.3.2 Performance

**PR.1** The system shall support one active user at a time.

**PR.2** The system shall support at least 10 user profiles, each consisting of at least 50 debts, 50 investments/savings, and 120 budgets, where a single budget may contain up to 100 entries.

**PR.3** The system shall complete and display calculations (investment growth/debt payoff) within 3 seconds for 90% of calculations, with projection periods of up to 100 years.

**PR.4** The system shall update budget totals within 1 second (for 90% of entries) of being added, edited or removed.

**PR.5** The system shall render graphs and tables within 3 seconds (for 90% of calculations) of completion.

**PR.6** The system shall save and load a profile within 5 seconds for 90% of saves/loads.

#### 6.3.3 Reliability/Dependability

**DR.1** The system shall produce identical results when identical data is entered.

**DR.2** The system results shall match independently computed reference values within $0.01.

**DR.3** The system shall not terminate unexpectedly in response to any user entries.

**DR.3.1** The system shall display messages for any errors that are encountered and preserve data that has been input.

#### 6.3.4 Security

**SR.1** The system shall only save data to the user's device.

**SR.2** The system shall collect and store only data items outlined in R.14.

**SR.3** The system shall not create, modify, or delete saved data unless it is the result of user action.

**SR.4** The system shall not display or use data from any users other than the active user.

#### 6.3.5 Maintainability

**MR.1** The system shall keep calculation logic, UI, and data storage separate.

**MR.2** The system shall implement calculation logic in one place, utilized by other features that depend on it.

**MR.3** The system shall have API documentation comments for each class and public method stating the purpose, inputs, and outputs.

**MR.4** All variables, classes, and methods shall have descriptive names indicating their purpose.
