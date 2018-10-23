# Entity-Relationship Model

Model of different entities inside our application.
What are the business needs ? Then normalize and design the data model.

### Design and modeling Process

- What are the data needs of the users. Think as entities.
- Choose a data model, translate requirements into conceptual schema.
- Describe operations performed on the data.

Start with a logical design down to a physical design (DBMS).


- **Entity** : anything that is distinguishable from other objects.
- **Entity-sets** : An entity will be described as a set of attributes.
- **Relationship** : Association between entities.

A relationship may have an attribute. Most of relationships are binary.
Represent them in E-R diagram.

### Constraints

**Mapping Cardinalities** can be 1:1 -- 1:N -- N:1 -- N:N

**Composite Attributes** may be composed of several sub-attributes.

**Redundant Attributes** may appear in order for the table to be reconstructible.

Weak attributes

### E-R Diagrams

- Entities as rectangles.
- Attributes in rectangle, key underlined.
- Relationships are diamonds.
- If a relationship has an attribute, we link it to an attribute rectangle above.
With a double line, we represent "a student must at least have one advisor"
- We represent Roles with a dual link diamond, one with input, one with output constraint
- Cardinality X:X : Arrow is one, line is many.
- A double-lined diamond represents a weak attribute that may appear.