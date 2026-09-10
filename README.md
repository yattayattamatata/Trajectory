# Trajectory

Trajectory is a personal finance tool that helps users understand how their financial decisions play out over time — combining budgeting, debt payoff, and investment growth into a single, visual system.

## Purpose

Most people manage money through memory, spreadsheets, or basic banking apps rather than a structured system, and financial literacy around compounding interest is low. Debt accrues interest that compounds against the borrower (APR), while savings and investments compound in the user's favor (APY) — yet few tools show both sides side by side, tied to a real budget.

Trajectory bridges that gap by letting users see, in one place:
- What their debt is actually costing them over time
- How much their investments and savings could grow
- What budgeting changes they'd need to make to reach a target outcome

## Goals

- Let users build a budget and see where their income is going
- Let users enter debt balance and APR to visualize total interest cost over time, and how different payment amounts change that cost
- Let users enter savings/investment balance, contributions, and APY to project growth under multiple scenarios (conservative, historical, optimistic)
- Let users compare financial scenarios via graphs
- Let users set a debt-payoff timeframe and calculate the required monthly payment to hit it

## Who it's for

**Key users** — individuals actively managing their own finances: college students, young adults, working adults, and anyone managing debt, savings, or investments. They enter their own financial data and use the system to compare scenarios and make decisions.

**Secondary users** — teachers, financial advisors, parents, or mentors who use Trajectory to demonstrate financial concepts to someone else rather than track their own finances.

The system prioritizes accuracy and clarity for key users above all else, while still including basic input validation (no negative balances, impossible rates, or blank required fields) to keep results meaningful for everyone.

## Status

Early development — this is a course project (CMPSC 411) built collaboratively as a group.

## Getting Started

The simplest way to get set up, regardless of what code editor you use:

1. **Install [GitHub Desktop](https://desktop.github.com/)** — no terminal or SSH keys required.
2. **Sign in** with your GitHub account (opens a normal browser login).
3. **Clone the repo**: File → Clone Repository → search "Trajectory" (or paste `https://github.com/yattayattamatata/Trajectory`) → pick a folder → Clone.
4. **Open the cloned folder** in whatever editor you use (VS Code, VSCodium, IntelliJ, etc.) — GitHub Desktop often shows a shortcut button for this automatically.
5. **Make your changes**, then switch back to GitHub Desktop. It lists everything you changed — write a short commit summary, click **Commit to main** (or your feature branch), then **Push origin**.
6. **Before starting new work**, click **Fetch origin** then **Pull origin** in GitHub Desktop to make sure you're not working on an outdated copy.

If you're comfortable with git via terminal/SSH instead, that works too:

```bash
git clone git@github.com:yattayattamatata/Trajectory.git
cd Trajectory
```

*(Build/run instructions to be added as the project takes shape.)*

## Contributing

This is a group project — coordinate branches and pull requests with the team before merging into `main`.