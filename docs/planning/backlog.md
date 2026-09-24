# Trajectory: Product Backlog

*Per the DEEP principle, this backlog is not expected to be complete at this stage. Items closer to being worked on are described in greater detail; items further out remain intentionally high-level.*

## Backlog Items

1. Create and manage a personal budget for each user (LARGE)
2. Add and track debts with APR and monthly payments (SMALL)
3. Calculate a debt payoff timeline based on a monthly payment (MEDIUM)
4. Add and track investments with APY and monthly contributions (SMALL)
5. Project investment growth over time with different rates of return (MEDIUM)
6. Display debt and investment projections using graphs (LARGE)
7. Save a user's financial information for future use (MEDIUM)
8. Load a previously saved user's financial information (LARGE)
9. Create the graphical desktop interface for Trajectory (LARGE)
10. Validate financial information entered by the user (X-SMALL)

---

## Detailed Backlog Items

The following three items have been selected for detailed description, as they represent near-term, well-understood work with the clearest path to implementation.

### Item 2: Add and track debts with APR and monthly payments (SMALL)

**User Story**

As a user, I want to add a debt with its balance, APR, and minimum payment, so that Trajectory can track what I owe and use it in future calculations.

**Acceptance Criteria**

- User can enter a debt's name/label, starting balance, APR, and minimum payment.
- The debt is added to the user's list of debts and persists for the duration of the session.
- The system rejects invalid values (e.g., a negative balance or negative APR) per Item 10.
- User can view a list of all debts they have added, showing at minimum the debt's name and current balance.

---

### Item 3: Calculate a debt payoff timeline based on a monthly payment (MEDIUM)

**User Story**

As a user, I want to enter a monthly payment amount for a specific debt, so that I can see how many months it will take to pay off and how much interest I will pay in total.

**Acceptance Criteria**

- User selects an existing debt and enters a proposed monthly payment amount.
- The system calculates and displays the number of months until the debt reaches a zero balance.
- The system calculates and displays the total interest paid over the payoff period.
- If the entered payment does not exceed the interest accruing on the current balance, the system informs the user the debt cannot be paid off at that payment amount, rather than displaying an incorrect or infinite timeline.
- User can view the projected balance remaining at the end of each month over the payoff period.

---

### Item 10: Validate financial information entered by the user (X-SMALL)

**User Story**

As a user, I want the system to reject invalid financial inputs, so that my projections and calculations remain accurate and I do not receive misleading results from bad data.

**Acceptance Criteria**

- The system rejects negative values for balance, APR/APY, and monthly payment/contribution fields.
- The system rejects blank or empty required fields.
- The system flags unrealistic values (e.g., an APR far outside a reasonable range) rather than silently accepting them.
- The user receives a clear, specific error message identifying which field is invalid and why, rather than a generic failure message.
