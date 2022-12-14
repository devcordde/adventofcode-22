# head 𝕊 tail -> new tail pos
AdjustTail ← (∧´1≥|∘-)◶(⊢+×∘-)‿⊢

Step ← { knots 𝕊 move:
  newH ← <move+⊑knots
  newTs ← newH AdjustTail` 1↓knots
  newH ∾ newTs
}

Solve ← { ≠⍷(𝕨-1)⊸⊑¨(<𝕨/⋈0‿0) Step` 𝕩 }

Parse ← { (•BQN 2↓𝕩)/⋈(⊑"DURL"⊐⊑𝕩)⊑⟨0‿1,0‿¯1,1‿0,¯1‿0⟩ }
input ← ∾Parse¨ •FLines ⊑ •args

•Out 2 Solve input
•Out 10 Solve input
