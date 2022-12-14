MatchingPairs ← (+´<´∘⍋¨/1+↕∘≠) (2/⟜↕2÷˜≠)⊸⊔
DecoderKey ← ×´1+0‿1⊐˜⍋∘∾˜⟜⟨⟨2⟩⟩‿⟨⟨6⟩⟩
Parse ← •BQN∘("[]"{(𝕘⊏˜𝕗⊸⊐)⌾((𝕩∊𝕗)⊸/)𝕩}"⟨⟩")¨ ⟨⟩⊸≢¨⊸/
input ← Parse •FLines ⊑ •args

•Out MatchingPairs input
•Out DecoderKey input
