## Jordan Beth - PA 1

### Java Version
- Java version "1.8.0_144"
- Java(TM) SE Runtime Environment (build 1.8.0_144-b01)
- Java HotSpot(TM) 64-Bit Server VM (build 25.144-b01, mixed mode)

### Eclipse Used
- Eclipse IDE for Java Developers
- Version: 2018-12 (4.10.0)
- Build id: 20181214-0600

### Operating System Specifications
- macOS Mojave 10.14.2

### Notes

##### Problem 2
- Overall: The operands are separated with the character 'b'. For addition, subtraction, and multiplication (bitwise and), there are constraints. The main constraint is that larger values must be on the left side of the 'b' character.

##### Problem 2B
- For part iii of problem 2B, I implemented the multiplication problem as a bitwise &. If the left operand is longer than the right operand, the remaining digits in the left operand are anded with 0.

Example tape: 10110b0011
1. Decrement the second argument to the right of b (0011)
2. Copy the first argument to the front of the tape separated by a different delimiter (a) so the tape looks like the following:
    - 10110a10110b0011
3. Add the argument between a and b to the value on the left of a
4. Restore the argument between a and b back to its original state.
5. Return to step 1 and continue until the second argument to the right of b (0011) is all 0's
6. At this point, the value to the left of delimiter a would be the product.
