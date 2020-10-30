import ../../lib/shim as nim_shim
import ../sys

proc hashMessage*(message: cstring): cstring =
  let hash = nim_shim.hashMessage($message)
  result = cast[cstring](c_malloc(csize_t hash.len + 1))
  copyMem(result, hash.cstring, hash.len)
  result[hash.len] = '\0'

proc generateAlias*(pubKey: cstring): cstring =
  let alias = "default_alias" #nim_shim.generateAlias($pubKey)
  echo "StatusModule: ###nim generateAlias 1"
  result = cast[cstring](c_malloc(2*csize_t alias.len + 1))
  echo "StatusModule: ###nim generateAlias 2"
  copyMem(result, alias.cstring, alias.len)
  echo "StatusModule: ###nim generateAlias 3"
  result[alias.len] = '\0'

proc identicon*(pubKey: cstring): cstring =
  let icon = nim_shim.identicon($pubKey)
  result = cast[cstring](c_malloc(csize_t icon.len + 1))
  copyMem(result, icon.cstring, icon.len)
  result[icon.len] = '\0'

