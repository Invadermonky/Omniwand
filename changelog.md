# Omniwand Changelog
## 1.12.2-2.0.3
- Fixed rare server desync crash

---

## 1.12.2-2.0.2
- Fixed translation with wand when transforming or reverting
- Fixed a couple of weird network handling issues

---

## 1.12.2-2.0.1
### Fixed
- Fixed Omniwand event handler causing an infinite item/block exploit

---

## 1.12.2-2.0.0
### IMPORTANT
Internal handling for both the wand logic and the configuration has changed. Be sure to remove all items from existing wands 
before updating and resetting your configuration after updating.

### Added
- Added `zh_ch.lang` courtesy of mczph

### Removed
- Removed the "staff" Omniwand appearance, the wand will now only use the "wand" appearance

### Changed
- Rewrote configuration for more precise control over filtered items
- General code cleanup and improved internal handling
- Vanilla items can now be attached to the Omniwand

### Fixed
- Fixed a rare crash that occurred when omniwand items were broken by non-player sources
- Fixed double-equip animation that sometimes played on servers

---

## Omniwand 1.12.2-1.0.1
- Fixed an issue with mod aliases not working correctly
- Added basic information tooltip
- Improved restricted tooltip to better support large numbers of attached items
- Improved Wand GUI to better support large numbers of attached items

---

## 1.12.2-1.0.0
- Initial Release
