_allSides ← {F _a 𝕩: {F⌾(⌽∘⍉⍟𝕨)𝕩}⟜𝕩¨ ↕4 }
# Part 1
Visibilities ← (⊑>⌈´∘↓˜⟜1)¨∘(¯1↓↓)˘
CountVisible ← {+´⥊∨´Visibilities _allSides 𝕩}
# Part 2
ViewDistances ← (-⟜1∘≠⌊1+1⊐˜⊑≤1↓⊢)¨∘(¯1↓↓)˘
MaxScenicScore ← {⌈´⥊×´>¨ViewDistances _allSides 𝕩}

input ← > -⟜'0' •FLines ⊑ •args
•Out CountVisible input
•Out MaxScenicScore input
