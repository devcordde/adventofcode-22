Split ← (⊢-˜¬×+`)∘=⊔⊢
Parse ← •BQN⚇¯2 '-'⊸Split¨∘(','⊸Split)
FullCover ← 0≥×´∘-´
PartCover ← ∨´0≥(×´¨-⟜⊑⋈⊑⊸-)´
input ← Parse¨ •FLines ⊑ •args
•Show +´¨ (FullCover¨⋈PartCover¨) input
