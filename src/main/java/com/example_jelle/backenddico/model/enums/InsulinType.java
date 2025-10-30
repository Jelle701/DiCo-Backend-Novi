// Enum representing common types of insulin.
package com.example_jelle.backenddico.model.enums;

public enum InsulinType {
    // Rapid-acting
    HUMALOG,
    NOVORAPID,
    FIASP,
    APIDRA,
    AFREZZA,

    // Short-acting
    HUMULIN_R,
    NOVOLIN_R,

    // Intermediate-acting
    HUMULIN_N,
    NOVOLIN_N,

    // Long-acting
    LANTUS,
    LEVEMIR,
    TOUJEO,
    TRESIBA,
    BASAGLAR,
    REZVOGLAR,
    SEMGLEE,

    // Ultra-long acting
    AWIQLI,

    // Premix / Co-formulated
    HUMALOG_MIX_75_25,
    NOVOLOG_MIX_70_30,
    RYZODEG_70_30,

    // Other
    OTHER
}
