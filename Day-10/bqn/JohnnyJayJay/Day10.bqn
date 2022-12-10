Cycles ← ⊑¨⊸∾⟜0/1∾1++`∘(1⊸⊑¨)
SigStrengthSum ← +´ ⊢×(+⟜1×39=40|-⟜20)∘↕∘≠
Pixels ← {⊑⟜".#"¨1≥|(↕40)⊸-˘6‿40⥊𝕩}

Parse ← =⟜4∘≠◶⟨2⋈•BQN∘↓˜⟜5, 1‿0⟩
input ← Cycles Parse¨ •FLines ⊑ •args

•Out SigStrengthSum input
•Show Pixels input
