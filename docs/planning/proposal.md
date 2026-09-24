# Trajectory: Project Proposal

## 1. Purpose

Most Americans manage their money with bank apps, spreadsheets, or through memory rather than using a structured system. Annual Percentage Rate (APR) is often viewed as the cost of borrowing money, including interest, fees, and additional costs. Anytime debt is accrued and not paid, the borrower is charged interest on the total amount, which means after the first month the borrower begins to owe interest on interest. Annual Percent Yield (APY), on the other hand, is the interest rate earned annually, including compounding interest. Interest can compound at different intervals (daily, monthly, quarterly, etc.), and depending on the rate and how often compounding occurs, this will determine how fast investments grow. Individuals will either accumulate debt and be subject to APR, or accumulate investments/savings and reap the benefits of APY. What separates which form of compounding interest one experiences often stems from how an individual budgets their income, and whether they are living within their means. This tool will be at the forefront of all three subjects to give users one place to plan and understand their finances.

These three concepts sit at the core of finances, governing nearly every financial outcome; however, most Americans do not understand or utilize them properly. Americans on average only answered 49% of TIAA's index questions correctly in 2025 (Generation Z only answering 38% correctly), with compounding-debt questions scoring worst of any topic (Yakoboski et al., 2025). Not only are people ill-informed about debt, but the average American credit card debt is $11,507 with an APR of 22.15%, carrying significant, growing interest costs as a result (Shepard, 2026). On the other hand, the S&P 500 has returned on average 10% since its 1957 inception, with the trailing 40 years sitting at 11.5% as of December 2025 (*What Is the S&P 500 and Stock Market Average Return?*, 2026). Most Americans do not understand how much passive compounding interest can earn them and often leave money uninvested or fail to see what steady contributions can become. When it comes to budgeting, 37% of Americans could not afford a $400 emergency with cash or a cash equivalent, and only 55% have a 3-month buffer (*Savings and Investments*, n.d.). Without a proper budget and a savings cushion to help survive emergencies, Americans are unable to pay down debt or invest, even if they do understand the concepts. Overall, most Americans do not realize the true cost of compounding interest or the potential that compounding investments can get them, and lack a system that can help them determine how to get from one outcome to the other.

This proposed personal finance tool will bridge the gap for many Americans, allowing them to visually see the long-term effects of both sides of compounding interest, as well as make a budget to achieve their desired result. Users will be able to input their debts, balance, and APR, and see the real cost over time, as well as how those debts can be affected by accelerated payments. Users will also be able to input a contribution amount, APY, and starting balance, and visualize projected growth, potentially with multiple scenarios (conservative, historical, and optimistic). Finally, there will be a budget view that shows how money is allocated, and how those choices will help to pay off debt or increase investment growth. After using this tool, users will be able to know what their debt is costing them, how much money their investments could earn them, and what they need to do with their income to get to their desired end state.

## 2. Scope

### 2.1 System Name

The proposed system will be called **Trajectory**.

### 2.2 Goals

The goal of Trajectory is to provide users with a better understanding of their finances and how their financial decisions can affect them in the future. Users will be able to create a budget to see where their money is going and how much they will have available for investments, savings, and debt. Users will also be able to enter their debt balance and APR to see how much their debt will cost them over time and how different payment amounts will affect the amount of interest they pay. Trajectory will also allow users to enter their savings or investment balance, contributions, and APY to visualize how their savings and investments could grow over time. Users will be able to compare these different financial scenarios through graphs to better understand their results. Trajectory will also allow users to set a timeframe for when they would like to have their debt paid off and calculate how much they would need to pay each month to reach that goal.

### 2.3 Application and Benefits

Trajectory can be used by individuals who are looking for a way to better manage and understand their personal finances. Instead of having to use different resources for budgeting, debt, savings, and investments, users will be able to access this information in one place. Trajectory will allow users to compare different financial choices and see how those choices may affect them over time. This can benefit users by making financial information easier to understand and helping them make more informed decisions with their money.

## 3. User Characteristics

### 3.1 Key Users

*(Critical to the continued success of the product. Greater importance is given to requirements generated by this category of user.)*

These are individuals who want to better understand and manage their personal finances. This includes college students, young adults, working adults, and people who are trying to manage debt, savings, or investments. Their main responsibility when using the product is to enter accurate financial information, such as income, expenses, debt balances, interest rates, savings amounts, and contribution amounts. They will use the system to compare financial scenarios and understand how their choices may affect them over time.

### 3.2 Secondary Users

*(Will use the product, but their opinion of it has no effect on its long-term success. Where there is a conflict between secondary users' requirements and those of key users, the key users take precedence.)*

Secondary users of Trajectory may include teachers, financial advisors, parents, or mentors who want to help someone understand financial planning. These users may not be using the system for their own personal finances, but they may use it to demonstrate how debt repayment, budgeting, saving, or investing works. Their role would be to guide another person through the system and help explain the results. These users may care more about clear examples and simple reports. Overall, their feedback is useful, but the system should focus mostly on the needs of the key users who will rely on Trajectory for their own financial planning.

### 3.3 Unimportant Users

*(Given the lowest priority. Includes infrequent, unauthorized, and unskilled users, as well as people who misuse the product.)*

Unimportant users include people who are not interested in personal finance, people who enter fake or random information, unauthorized users, or people who try to misuse the system. These users are given the lowest priority because they do not represent the main audience for Trajectory. The system should still include basic input validation so that users cannot enter invalid values, such as negative debt balances, impossible interest rates, or blank required fields.

Their subject matter experience and technological experience may vary, but they should not strongly influence the design of the system. The main design should focus on serious users who want accurate and understandable financial information. However, preventing incorrect input is still important, because bad data could lead to misleading results.

## 4. Technical Approach

### 4.1 Platform / Language

We plan to primarily use Java for this project. Java will allow the program to remain the same regardless of what OS the team members are using. Additionally, Java provides a large, well-documented set of libraries that we may use for different aspects of the project (GUI, data visualization, and database operations). Additionally, we intend to use Git/GitHub to share the project with team members. Git/GitHub offers versioning support, branching, and commenting, so that we can clearly communicate changes and keep everyone on the same sheet of music. We also plan to utilize Microsoft Teams to communicate with each other, conduct meetings, share resources, and collaboratively work on assignments.

### 4.2 Existing Tools

We have not fully settled on what specific packages we will need. We intend to do more research and decide as a group on what GUI library would work best (JavaFX and Swing). We are also working to determine which database tool would work best for us, both to hold data (SQLite vs. MySQL vs. Postgres) and to communicate from Java to the database (JDBC). JavaFX is the leading GUI candidate for simplicity and graphing support (critical for visualizing investments and debt over time), and SQLite is the leading database backend candidate because it does not require a separate server setup.

### 4.3 Risks

- Formulas may be incorrect or incomplete
- The system may be mistaken for professional financial advice, so a disclaimer may be needed
- Project scope may become too large for the semester timeline
- Selected tools may not integrate well together
- Failure to effectively manage code as a team
- Poor communication may cause duplicated work or missed tasks

## Works Cited

Savings and Investments. (n.d.). Board of Governors of the Federal Reserve System. Retrieved August 29, 2026, from https://www.federalreserve.gov/publications/2026-economic-well-being-of-us-households-in-2025-savings-investments.htm

Shepard, D. (2026, August 11). *2026 Credit Card Debt Statistics.* LendingTree. https://www.lendingtree.com/credit-cards/study/credit-card-debt-statistics/

What is the S&P 500 and stock market average return? | Fidelity. (2026, March 6). https://www.fidelity.com/learning-center/trading-investing/sp-500-average-return

Yakoboski, P. J., Lusardi, A., & Sticha, A. (2025). *Financial literacy and retirement fluency in America.* https://www.tiaa.org/public/institute/publication/2025/financial-literacy-and-retirement-fluency-in-america
