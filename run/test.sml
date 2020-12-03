You can have comments at the beginning and end of the file.
{
    :Keys without a value are considered as comments.

    Comments can also have new lines in them.:
    :int(10):
    :decimal(10.1):
    :hex(0x10):
    :bin(0b10):
    :text"Test owo uwu":
    :object{
        :booleanF(false):
        :booleanT(true):
        :string"This is a string.\nnew line \n\ttabbed\
aha not on new line\'

this is though. \" a":
        :obj{
            :cool(17):
        }:
        :array[
            (0.4)
            (true)
            {
                :cool(3):
            }
            "String"
        ]:
    }:
}
These beginning and end comments save in the map but shouldn't be used.