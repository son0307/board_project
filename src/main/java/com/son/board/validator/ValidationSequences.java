package com.son.board.validator;

import jakarta.validation.GroupSequence;

import com.son.board.validator.ValidationGroups.*;

@GroupSequence({NotBlankGroup.class, SizeGroup.class, PatternGroup.class})
public interface ValidationSequences {
}
