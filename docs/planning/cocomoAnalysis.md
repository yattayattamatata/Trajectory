# Trajectory: COCOMO II Early Design Cost Estimate

## B Calculation

| Category | Description | Rating [1–5] |
|---|---|---|
| Architecture/risk resolution | Reflects the extent of risk analysis carried out. Very Low means little analysis; Extra-High means a complete and thorough risk analysis. | 3 |
| Development flexibility | Reflects the degree of flexibility in the development process. Very Low means a prescribed process is used; Extra-High means the client sets only general goals. | 5 |
| Precedentedness | Reflects the previous experience of the organization with this type of project. Very Low means no previous experience; Extra-High means the organization is completely familiar with this application domain. | 2 |
| Process maturity | Reflects the process maturity of the organization. Computed from the CMM Maturity Questionnaire, or estimated by subtracting the CMM process maturity level from 5. | 1 |
| Team cohesion | Reflects how well the development team knows each other and works together. Very Low means very difficult interactions; Extra-High means an integrated, effective team with no communication problems. | 4 |
| **Total** | | **15** |

**Formula:**
```
B = (sum of 5 categorical ratings [1,5] / 100) + 1.01
B = (15 / 100) + 1.01
B = 1.16
```

## M Calculation

**Formula:** `M = PERS × RCPX × RUSE × PDIF × PREX × FCIL × SCED`

| Cost Driver | Description | Rating | Multiplier |
|---|---|---|---|
| PERS | Personnel capability | Nominal | 1.00 |
| RCPX | Product reliability and complexity | Nominal | 1.00 |
| RUSE | Required reuse | Low | 0.95 |
| PDIF | Platform difficulty | Low | 0.87 |
| PREX | Personnel experience | Low | 1.22 |
| FCIL | Team support facilities | Nominal | 1.00 |
| SCED | Required development schedule | High | 1.00 |

```
M = 1.00 × 1.00 × 0.95 × 0.87 × 1.22 × 1.00 × 1.00
M = 1.0083
```

## COCOMO II Early Design Effort Estimate

**Formula:**
```
PM = A × SIZE^B × M
```
Where:
- **PM** = estimated effort in person-months
- **A** = model constant
- **SIZE** = estimated project size in KLOC
- **B** = scale exponent
- **M** = product of effort multipliers

### Available Team Effort

The Trajectory team consists of four members and has approximately three months to complete the project.
```
Available effort = 4 members × 3 months = 12 person-months
```

### Model Constant
```
A = 2.94
```

### Estimated Project Size

Trajectory is estimated to contain approximately 3,000 lines of code.
```
SIZE = 3,000 LOC = 3 KLOC
```

### Calculating the Exponent B

The 5 scale factor values selected by the team are 3, 5, 2, 1, and 4.
```
B = (15 / 100) + 1.01 = 1.16
```

### Calculating the Effort Multiplier M
```
M = 1.00 × 1.00 × 0.95 × 0.87 × 1.22 × 1.00 × 1.00 = 1.0083
```

### Calculating the Estimated Effort
```
PM = A × SIZE^B × M
PM = 2.94 × 3^1.16 × 1.0083
PM ≈ 10.60 person-months
```

## Conclusion

Since our team has 4 members and the project timeline is approximately 3 months, our target effort is **12 person-months**. Based on our COCOMO II Early Design Model calculation, Trajectory is estimated to require approximately **10.60 person-months** of effort. Compared with the team's available 12 person-months, this leaves approximately **1.40 person-months** of remaining capacity. This slack provides our group with margin for additional features, exploration of unfamiliar tools (JavaFX and SQLite/JDBC), and any other unplanned complexity.

## Source

Center for Software Engineering, University of Southern California. (2000). *COCOMO II Model Definition Manual* (Version 2.1). https://student.cs.uwaterloo.ca/~cs430/CII_modelman2000.0.pdf
